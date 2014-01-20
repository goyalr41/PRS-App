<?php

/**
 * File to handle all API requests
 * Accepts GET and POST
 * 
 * Each request will be identified by TAG
 * Response will be JSON data

  /**
 * check for POST request 
 */
if (isset($_POST['tag']) && $_POST['tag'] != '') {
    // get tag
    $tag = $_POST['tag'];

    // include db handler
    require_once 'include1/DB_Functions1.php';
    $db = new DB_Functions1();

    // response Array
    $response = array("tag" => $tag, "success" => 0, "error" => 0);
	
	if ($tag == 'newsletter') {
		$mobile = $_POST['mobile'];
        $email = $_POST['email'];
		$user = $db->newsletter($mobile, $email);
		
		if ($user != false) {
            
            $response["success"] = 1;
//$response["user"]["mobile"] = $user["mobile"];
  //          $response["user"]["address"] = $user["address"];
            echo json_encode($response);
        } else {
            // user not found
            // echo json with error = 1
            $response["error"] = 1;
            $response["error_msg"] = "Incorrect mobile or email!";
            echo json_encode($response);
        }
	}
    // check for tag type
    if ($tag == 'delivery') {
        // Request type is check Login
        $mobile = $_POST['mobile'];
        $address = $_POST['address'];

        // check for user
        $user = $db->delivery($mobile, $address);
		
        if ($user != false) {
            
            $response["success"] = 1;
            $response["user"]["mobile"] = $user["mobile"];
            $response["user"]["address"] = $user["address"];
            echo json_encode($response);
        } else {
            // user not found
            // echo json with error = 1
            $response["error"] = 1;
            $response["error_msg"] = "Incorrect mobile or address!";
            echo json_encode($response);
        }
    } else if ($tag == 'event') {
        // Request type is Register new user
        $mobile = $_POST['mobile'];
        $location = $_POST['location'];
		$date = $_POST['date'];
		$time = $_POST['time'];
		$event_type = $_POST['event_type'];
		$num_people = $_POST['num_people'];
		$extras = $_POST['extras'];

        $user = $db->event($mobile,$location,$date,$time,$event_type,$num_people,$extras);
        if ($user) {
                // user stored successfully
                $response["success"] = 1;
               // $response["uid"] = $user["unique_id"];
            //    $response["user"]["mobile"] = $user["mobile"];
             //   $response["user"]["location"] = $user["location"];
             //   $response["user"]["date"] = $user["date"];
              //  $response["user"]["time"] = $user["time"];
			//	$response["user"]["event_type"] = $user["event_type"];
			//	$response["user"]["num_people"] = $user["num_people"];
			//	$response["user"]["extras"] = $user["extras"];
                echo json_encode($response);
        } else {
                // user failed to store
                $response["error"] = 1;
                $response["error_msg"] = "Error occured in Registartion";
                echo json_encode($response);
        }
        
    } else if ($tag == 'table_booking') {
        // Request type is Register new user
        $mobile = $_POST['mobile'];
        $location = $_POST['location'];
		$date = $_POST['date'];
		$time = $_POST['time'];
		//$event_type = $_POST['event_type'];
		$num_people = $_POST['num_people'];
		$extras = $_POST['extras'];

		if($db->sitting_space($time,$date,$location,$num_people)){
			$response["error"] = 2;
            $response["error_msg"] = "Sorry no space available!!";
            echo json_encode($response);
		}else{
			$user = $db->book_table($mobile,$location,$date,$time,$num_people,$extras);
			if ($user) {
                // user stored successfully
                $response["success"] = 1;
              //  $response["user"]["mobile"] = $user["mobile"];
              //  $response["user"]["location"] = $user["location"];
               // $response["user"]["date"] = $user["date"];
               // $response["user"]["time"] = $user["time"];
			//	$response["user"]["num_people"] = $user["num_people"];
			//	$response["user"]["extras"] = $user["extras"];
                echo json_encode($response);
			}
		}
	} else if ($tag == 'food_booking') {
        // Request type is Register new user
        $foodname = $_POST['food_name'];
        $quantity = $_POST['quantity'];
		$cost = $_POST['cost'];
		$type = $_POST['type'];
		//$event_type = $_POST['event_type'];
		//$num_people = $_POST['num_people'];
		//$extras = $_POST['extras'];

        $user = $db->food_table($foodname,$quantity,$cost,$type);
        if ($user) {
                // user stored successfully
                $response["success"] = 1;
                $response["user"]["food_name"] = $user["food_name"];
                $response["user"]["quantity"] = $user["quantity"];
                $response["user"]["cost"] = $user["cost"];
                $response["user"]["type"] = $user["type"];
				//$response["user"]["num_people"] = $user["num_people"];
				//$response["user"]["extras"] = $user["extras"];
                echo json_encode($response);
        }
	}
}
?>
