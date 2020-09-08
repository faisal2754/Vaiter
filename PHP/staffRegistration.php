<?php

    require_once 'dbOperations.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST'){
        if (
            isset ($_POST['FirstName'], 
            $_POST['LastName'],
            $_POST['Email'],
            $_POST['RestaurantName'],
            $_POST['Password'] ))
            {
                $hashedPass = password_hash($_POST['Password'], PASSWORD_DEFAULT);
                $db = new dbOperations();
                $result = $db->createStaff(
                    $_POST['FirstName'],
                    $_POST['LastName'],
                    $_POST['Email'],
                    $_POST['RestaurantName'],
                    $hashedPass
                );
                if ($result == USER_CREATED){
                    $response['error'] = false;
                    $response['message'] = "User registered successfully";
                } else if ($result == USER_FAILURE){
                    $response['error'] = true;
                    $response['message'] = "Could not create user";
                } else if ($result == USER_EXISTS){
                    $response['error'] = true;
                    $response['message'] = "User already exists";
                }
        } else {
            $response['error'] = true;
            $response['message'] = "Required fields are missing";
        }

    } else {
        $response['error'] = true;
        $response['message'] = "Invalid Request";
    }

    echo json_encode($response);

?>
