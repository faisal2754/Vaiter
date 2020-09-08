<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Vaiter -- Verification</title>
    <link href="style.css" type="text/css" rel="stylesheet" />
</head>
<body>
    <!-- start header div --> 
    <div id="header">
        <h2>Vaiter - Verification</h2>
    </div>
    <!-- end header div -->   
     
    <!-- start wrap div -->   
    <div id="wrap">
        <!-- start PHP code -->
        <?php
            require_once 'dbOperations.php';

            $db = new dbOperations();

            if(isset($_GET['email']) && !empty($_GET['email']) AND isset($_GET['someunsuspiciouscharacters']) && !empty($_GET['someunsuspiciouscharacters'])){
                // Verify data
                $email = $_GET['email']; // Set email variable
                $pass = $_GET['someunsuspiciouscharacters']; // Set hash variable
                //$npass = mysqli_escape_string($db->con, $_GET['someunsuspiciouscharacters']);
                $resultC = $db->checkCustomerLink($email, $pass);
                $resultS = $db->checkStaffLink($email, $pass);
                if (($resultC) && (!$resultS)) {
                    $db->verifyCustomer($email);
                    echo '<div class="statusmsg">Your account has been activated, you can now log in.</div>';
                } else if (!$resultC && $resultS){
                    $db->verifyStaff($email);
                    echo '<div class="statusmsg">Your account has been activated, you can now log in.</div>';
                } else if ($resultC && $resultS){
                    $db->verifyCustomer($email);
                    $db->verifyStaff($email);
                    echo '<div class="statusmsg">The url is either invalid or you have already activated your account.</div>';
                } else if (!$resultC && !$resultS){
                    echo '<div class="statusmsg">The url is either invalid or you have already activated your account.</div>';
                }
            } else{
                // Invalid approach
                echo '<div class="statusmsg">Invalid approach, please use the link that has been sent to your email.</div>';
            }
        
        ?>
        <!-- stop PHP Code -->
    </div>
    <!-- end wrap div --> 
</body>

</html>