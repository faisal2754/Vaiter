<?php

    require_once 'dbOperations.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST'){
        if (
            isset ($_POST['MealName'], $_POST['DateTime'], $_POST['CusID']))
            {
                $db = new dbOperations();
                $result = $db->createOrder(
                    $_POST['MealName'],
                    $_POST['DateTime'],
                    $_POST['CusID']
                );
                if ($result == ORDER_CREATED){
                    $response['error'] = false;
                    $response['message'] = "Order created successfully";
                } else if ($result == ORDER_FAILURE){
                    $response['error'] = true;
                    $response['message'] = "Could not create order";
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