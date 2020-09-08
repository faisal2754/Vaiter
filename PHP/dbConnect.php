<?php

    class dbConnect{

        private $con;

        function connect(){

            include_once dirname (__FILE__).'/Constants.php';

            $this->con = new mysqli(dbHost,dbUser,dbPass,dbName);

            if (mysqli_connect_errno()){
                echo "error because ". mysqli_connect_error();
                return null;
            }

            return $this->con;
        }
    }
?>
