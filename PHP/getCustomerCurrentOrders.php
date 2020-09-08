<?php

    require_once 'dbOperations.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST'){
        if (isset ($_POST['CusID'])){
                $db = new dbOperations();
                $result = $db->showCustomerCurrentOrders($_POST['CusID']);
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