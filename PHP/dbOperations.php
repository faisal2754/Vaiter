<?php
use PHPMailer\PHPMailer\PHPMailer;
    
class dbOperations{
        
        private $con;

        function __construct(){
            require_once 'vendor/autoload.php';
            require_once dirname(__FILE__) . '/dbConnect.php';
            $db = new dbConnect();
            $this->con = $db->connect();
        }

        public function createCustomer($fname, $lname, $email, $pass){
            if (!$this->customerExists($email)){
                $statement = $this->con->prepare("INSERT INTO Vaiter_Customer (FirstName, LastName, Email, Password) VALUES (?,?,?,?)");
                $statement->bind_param("ssss", $fname, $lname, $email, $pass);
                if ($statement->execute()){
                    $this->sendCustomerVerificationEmail($email, $pass);
                    return USER_CREATED;
                } else {
                    return USER_FAILURE;
                }
            }
            return USER_EXISTS;
        }

        public function createStaff($fname, $lname, $email, $res, $pass){
            if (!$this->staffExists($email)){
                if ($q = mysqli_query($this->con, "SELECT RestaurantID FROM Vaiter_Restaurant WHERE Name = '$res'")){
                    $result = $q->fetch_assoc(); //guranteed to return only 1 row
                }
                $res_id = $result['RestaurantID'];
                $statement = $this->con->prepare("INSERT INTO Vaiter_Staff (FirstName, LastName, Email, RestaurantID, Password) VALUES (?,?,?,?,?)");
                $statement->bind_param("sssis", $fname, $lname, $email, $res_id, $pass);
                if ($statement->execute()){
                    $this->sendStaffVerificationEmail($email, $pass);
                    return USER_CREATED;
                } else {
                    return USER_FAILURE;
                }
            }
            return USER_EXISTS;
        }
        
        private function customerExists($email){
            $statement = $this->con->prepare("SELECT CustomerID FROM Vaiter_Customer WHERE Email = ?");
            $statement->bind_param("s",$email);
            $statement->execute();
            $statement->store_result();
            return $statement->num_rows() > 0;
        }

        private function staffExists($email){
            $statement = $this->con->prepare("SELECT StaffID FROM Vaiter_Staff WHERE Email = ?");
            $statement->bind_param("s",$email);
            $statement->execute();
            $statement->store_result();
            return $statement->num_rows() > 0;
        }

        public function customerLogin($email, $pass){
            if ($this->customerExists($email) && $this->customerIsVerified($email)){
                $hashed_pass = $this->getCustomerPasswordByEmail($email);
                if (password_verify($pass, $hashed_pass)){
                    return USER_AUTHENTICATED;
                } else {
                    return USER_WRONG_PASSWORD;
                }
            } else {
                return USER_NOT_FOUND;
            }
        }

        private function getCustomerPasswordByEmail($email){
            $statement = $this->con->prepare("SELECT Password FROM Vaiter_Customer WHERE Email = ?");
            $statement->bind_param("s",$email);
            $statement->execute();
            $statement->bind_result($pass);
            $statement->fetch();
            return $pass;
        }

        public function getCustomerByEmail($email){
            $statement = $this->con->prepare("SELECT CustomerID, FirstName, LastName, Email FROM Vaiter_Customer WHERE Email = ?");
            $statement->bind_param("s",$email);
            $statement->execute();
            $statement->bind_result($id, $fn, $ln, $em);
            $statement->fetch();
            $user = array();
            $user['CustomerID'] = $id;
            $user['FirstName'] = $fn;
            $user['LastName'] = $ln;
            $user['Email'] = $em; 
            return $user;      
        }

        public function staffLogin($email, $pass){
            if ($this->staffExists($email) && $this->staffIsVerified($email)){
                $hashed_pass = $this->getStaffPasswordByEmail($email);
                if (password_verify($pass, $hashed_pass)){
                    return USER_AUTHENTICATED;
                } else {
                    return USER_WRONG_PASSWORD;
                }
            } else {
                return USER_NOT_FOUND;
            }
        }

        private function getStaffPasswordByEmail($email){
            $statement = $this->con->prepare("SELECT Password FROM Vaiter_Staff WHERE Email = ?");
            $statement->bind_param("s",$email);
            $statement->execute();
            $statement->bind_result($pass);
            $statement->fetch();
            return $pass;
        }

        public function getStaffByEmail($email){
            $statement = $this->con->prepare(
                "SELECT Vaiter_Staff.StaffID, Vaiter_Staff.FirstName, Vaiter_Staff.LastName, 
                Vaiter_Staff.Email, Vaiter_Restaurant.Name AS RestaurantName
                FROM Vaiter_Staff INNER JOIN Vaiter_Restaurant 
                ON Vaiter_Staff.RestaurantID = Vaiter_Restaurant.RestaurantID 
                WHERE Email = ?");
            $statement->bind_param("s",$email);
            $statement->execute();
            $statement->bind_result($id, $fn, $ln, $em, $rn);
            $statement->fetch();
            $user = array();
            $user['StaffID'] = $id;
            $user['FirstName'] = $fn;
            $user['LastName'] = $ln;   
            $user['Email'] = $em;
            $user['RestaurantName'] = $rn;
            return $user;      
        }

        public function getMeals($res){
            $statement = $this->con->prepare(
                "SELECT Vaiter_Meal.Description
                FROM Vaiter_Meal INNER JOIN Vaiter_Restaurant 
                ON Vaiter_Meal.RestaurantID = Vaiter_Restaurant.RestaurantID 
                WHERE Vaiter_Restaurant.Name = ?");
            $statement->bind_param("s",$res);
            $statement->execute();
            $result = array();
            $statement->bind_result($meal);
            while ($statement->fetch()){
                $result[] = $meal;
            }
            return $result;
        }

        public function createOrder($mealName, $dateTime, $cusID){
            $q = mysqli_query($this->con, "SELECT MealID FROM Vaiter_Meal WHERE Description = '$mealName'");
            $result = $q->fetch_assoc();
            $mealID = $result['MealID'];
            $statement = $this->con->prepare("INSERT INTO Vaiter_Order(MealID, OrderTime) VALUES (?, ?)");
            $statement->bind_param("is", $mealID, $dateTime);
            if ($statement->execute() && $this->addToHistory($cusID) == 0){
                return ORDER_CREATED;
            } else {
                return ORDER_FAILURE;
            }
        }

        private function addToHistory($cusID){
            $q = mysqli_query($this->con, "SELECT MAX(OrderID) AS OrderID FROM Vaiter_Order");
            $result = $q->fetch_assoc();
            $orderID = $result['OrderID'];
            $statement = $this->con->prepare("INSERT INTO Vaiter_OrderHistory(CustomerID, OrderID) VALUES (?, ?)");
            $statement->bind_param("ii", $cusID, $orderID);
            if ($statement->execute()){
                return 0;
            } else {
                return 1;
            }
        }

        public function showCustomerCurrentOrders($cusID){
            $statement = $this->con->prepare("SELECT Vaiter_Meal.Description, Vaiter_Order.OrderTime, Vaiter_Restaurant.Name
            FROM Vaiter_OrderHistory
            INNER JOIN Vaiter_Order
            ON Vaiter_OrderHistory.OrderID = Vaiter_Order.OrderID
            INNER JOIN Vaiter_Meal
            ON Vaiter_Order.MealID = Vaiter_Meal.MealID
            INNER JOIN Vaiter_Restaurant
            ON Vaiter_Meal.RestaurantID = Vaiter_Restaurant.RestaurantID
            WHERE Vaiter_OrderHistory.CustomerID = ?
            AND Vaiter_Order.OrderTime > CURRENT_TIMESTAMP");
            $statement->bind_param("i",$cusID);
            $statement->execute();
            $result = array();
            $statement->bind_result($meal, $dTime, $resName);
            $i = 0;
            while ($statement->fetch()){
                $result[$i] = $meal;
                $result[$i+1] = $dTime;
                $result[$i+2] = $resName;
                $i = $i+3;
            }
            return $result;
        }

        public function showCustomerPastOrders($cusID){
            $statement = $this->con->prepare(
            "SELECT Vaiter_Meal.Description, 
            Vaiter_Order.OrderTime, 
            Vaiter_Restaurant.Name
            FROM Vaiter_OrderHistory
            INNER JOIN Vaiter_Order
            ON Vaiter_OrderHistory.OrderID = Vaiter_Order.OrderID
            INNER JOIN Vaiter_Meal
            ON Vaiter_Order.MealID = Vaiter_Meal.MealID
            INNER JOIN Vaiter_Restaurant
            ON Vaiter_Meal.RestaurantID = Vaiter_Restaurant.RestaurantID
            WHERE Vaiter_OrderHistory.CustomerID = ?
            AND Vaiter_Order.OrderTime < CURRENT_TIMESTAMP");
            $statement->bind_param("i",$cusID);
            $statement->execute();
            $result = array();
            $statement->bind_result($meal, $dTime, $resName);
            $i = 0;
            while ($statement->fetch()){
                $result[$i] = $meal;
                $result[$i+1] = $dTime;
                $result[$i+2] = $resName;
                $i = $i+3;
            }
            return $result;
        }

        public function showStaffCurrentOrders($staffID){
            $statement = $this->con->prepare(
            "SELECT Vaiter_Customer.FirstName, 
            Vaiter_Customer.LastName, 
            Vaiter_Meal.Description, 
            Vaiter_Order.OrderTime
            FROM Vaiter_Staff
            INNER JOIN Vaiter_Meal
            ON Vaiter_Staff.RestaurantID = Vaiter_Meal.RestaurantID
            INNER JOIN Vaiter_Order
            ON Vaiter_Meal.MealID = Vaiter_Order.MealID
            INNER JOIN Vaiter_OrderHistory
            ON Vaiter_Order.OrderID = Vaiter_OrderHistory.OrderID
            INNER JOIN Vaiter_Customer
            ON Vaiter_OrderHistory.CustomerID = Vaiter_Customer.CustomerID
            WHERE Vaiter_Staff.StaffID = ?
            AND Vaiter_Order.OrderTime > CURRENT_TIMESTAMP");
            $statement->bind_param("i",$staffID);
            $statement->execute();
            $result = array();
            $statement->bind_result($cFn, $cLn, $meal, $dTime);
            $i = 0;
            while ($statement->fetch()){
                $result[$i] = $cFn;
                $result[$i+1] = $cLn;
                $result[$i+2] = $meal;
                $result[$i+3] = $dTime;
                $i = $i+4;
            }
            return $result;
        }

        public function showStaffPastOrders($staffID){
            $statement = $this->con->prepare(
            "SELECT Vaiter_Customer.FirstName, 
            Vaiter_Customer.LastName, 
            Vaiter_Meal.Description, 
            Vaiter_Order.OrderTime
            FROM Vaiter_Staff
            INNER JOIN Vaiter_Meal
            ON Vaiter_Staff.RestaurantID = Vaiter_Meal.RestaurantID
            INNER JOIN Vaiter_Order
            ON Vaiter_Meal.MealID = Vaiter_Order.MealID
            INNER JOIN Vaiter_OrderHistory
            ON Vaiter_Order.OrderID = Vaiter_OrderHistory.OrderID
            INNER JOIN Vaiter_Customer
            ON Vaiter_OrderHistory.CustomerID = Vaiter_Customer.CustomerID
            WHERE Vaiter_Staff.StaffID = ?
            AND Vaiter_Order.OrderTime < CURRENT_TIMESTAMP");
            $statement->bind_param("i",$staffID);
            $statement->execute();
            $result = array();
            $statement->bind_result($cFn, $cLn, $meal, $dTime);
            $i = 0;
            while ($statement->fetch()){
                $result[$i] = $cFn;
                $result[$i+1] = $cLn;
                $result[$i+2] = $meal;
                $result[$i+3] = $dTime;
                $i = $i+4;
            }
            return $result;
        }

        private function sendCustomerVerificationEmail($email, $pass){
            $pass = $this->getCustomerPasswordByEmail($email);
            $mail = new PHPMailer();
            $mail->isSMTP();
            $mail->SMTPDebug = 0;
            $mail->SMTPOptions = array(
                    'ssl' => array(
                        'verify_peer' => false,
                        'verify_peer_name' => false,
                        'allow_self_signed' => true
                    )
                );
            $mail->Host = 'smtp.gmail.com';
            $mail->Port = 465;
            $mail->SMTPAuth = true;
            $mail->SMTPSecure = "ssl";
            $mail->Username = 'no.reply.vaiter@gmail.com';
            $mail->Password = 'Vaiter132';
            $mail->setFrom('no.reply.vaiter@gmail.com', 'no-reply-vaiter');
            $mail->addAddress($email);
            $mail->Subject = 'Vaiter Account Verification';
            $body = "Thanks for signing up!\n\nYour customer account has been created, you can log in after you verify your account with the link below.\n\nhttps://lamp.ms.wits.ac.za/home/s2095208/Vaiter/verify.php?email=$email&someunsuspiciouscharacters=$pass";
            $mail->Body    = $body;

            if(!$mail->Send()) {
                return "Error while sending Email.";
                //var_dump($mail);
            } else {
                return "Email sent successfully";
            }
        }

        private function sendStaffVerificationEmail($email, $pass){
            $pass = $this->getStaffPasswordByEmail($email);
            $mail = new PHPMailer();
            $mail->isSMTP();
            $mail->SMTPDebug = 0;
            $mail->SMTPOptions = array(
                    'ssl' => array(
                        'verify_peer' => false,
                        'verify_peer_name' => false,
                        'allow_self_signed' => true
                    )
                );
            $mail->Host = 'smtp.gmail.com';
            $mail->Port = 465;
            $mail->SMTPAuth = true;
            $mail->SMTPSecure = "ssl";
            $mail->Username = 'no.reply.vaiter@gmail.com';
            $mail->Password = 'Vaiter132';
            $mail->setFrom('no.reply.vaiter@gmail.com', 'no-reply-vaiter');
            $mail->addAddress($email);
            $mail->Subject = 'Vaiter Account Verification';
            $body = "Thanks for signing up!\n\nYour staff account has been created, you can log in after you verify your account with the link below.\n\nhttps://lamp.ms.wits.ac.za/home/s2095208/Vaiter/verify.php?email=$email&someunsuspiciouscharacters=$pass";
            $mail->Body    = $body;

            if(!$mail->Send()) {
                return "Error while sending Email.";
                //var_dump($mail);
            } else {
                return "Email sent successfully";
            }
        }

        public function checkCustomerLink($email, $hpass){
            $statement = $this->con->prepare("SELECT Email, Password, Verified 
            FROM Vaiter_Customer WHERE Email = ? AND Verified = 0");
            $statement->bind_param("s", $email);
            $statement->execute();
            $statement->store_result();
            $pass = $this->getCustomerPasswordByEmail($email);
            if ($statement->num_rows() > 0){
                if ($this->hashCmp($hpass, $pass) == 0){
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        public function checkStaffLink($email, $hpass){
            $statement = $this->con->prepare("SELECT Email, Verified 
            FROM Vaiter_Staff WHERE Email = ? AND Verified = 0");
            $statement->bind_param("s", $email);
            $statement->execute();
            $statement->store_result();
            $pass = $this->getStaffPasswordByEmail($email);
            if ($statement->num_rows() > 0){
                if ($this->hashCmp($hpass, $pass) == 0){
                    return true;   
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        private function hashCmp($secret, $userinput)
        {
            $sx = 0;
            $sy = strlen($secret);
            $uy = strlen($userinput);
            $result = $sy - $uy;
            for ($ux = 0; $ux < $uy; $ux++)
            {
                $result |= ord($userinput[$ux]) ^ ord($secret[$sx]);
                $sx = ($sx + 1) % $sy;
            }

            return $result;
        }

        public function verifyCustomer($email){
            $statement = $this->con->prepare("UPDATE Vaiter_Customer SET Verified = 1 WHERE Email = ?");
            $statement->bind_param("s", $email);
            $statement->execute();
        }

        public function verifyStaff($email){
            $statement = $this->con->prepare("UPDATE Vaiter_Staff SET Verified = 1 WHERE Email = ?");
            $statement->bind_param("s", $email);
            $statement->execute();
        }

        private function customerIsVerified($email){
            $statement = $this->con->prepare("SELECT Verified FROM Vaiter_Customer WHERE Email = ?");
            $statement->bind_param("s", $email);
            $statement->execute();
            $statement->bind_result($result);
            $statement->fetch();
            return $result == 1;
        }

        private function staffIsVerified($email){
            $statement = $this->con->prepare("SELECT Verified FROM Vaiter_Staff WHERE Email = ?");
            $statement->bind_param("s", $email);
            $statement->execute();
            $statement->bind_result($result);
            $statement->fetch();
            return $result == 1;
        }

    }
?>    