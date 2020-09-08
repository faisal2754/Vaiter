<?php

    require_once 'dbOperations.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST'){
        if (isset ($_POST['Email'], $_POST['Password'] )){
                $db = new dbOperations();
                $result = $db->customerLogin($_POST['Email'], $_POST['Password']);
                if ($result == USER_AUTHENTICATED){
                    $user = $db->getCustomerByEmail($_POST['Email']);
                    $response['error'] = false;
                    $response['message'] = "Login Success";
                    $response['user'] = $user;
                } else if ($result == USER_NOT_FOUND){
                    $response['error'] = true;
                    $response['message'] = "User does not exist";
                } else if ($result == USER_WRONG_PASSWORD){
                    $response['error'] = true;
                    $response['message'] = "Incorrect password";
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