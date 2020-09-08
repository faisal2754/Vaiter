<?php

    require_once 'dbOperations.php';

    if ($_SERVER['REQUEST_METHOD'] == 'POST'){
        if (isset ($_POST['Email'], $_POST['Password'] )){
            $db = new dbOperations();
            $result = $db->sendCustomerVerificationEmail($_POST['Email'], $_POST['Password']);
            $response['error'] = false;
            $response['message'] = $result;
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
