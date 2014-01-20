<?php

class DB_Functions1 {

    private $db;

    //put your code here
    // constructor
    function __construct() {
        require_once 'DB_Connect1.php';
        // connecting to database
        $this->db = new DB_Connect1();
        $this->db->connect();
    }

    // destructor
    function __destruct() {
        
    }

    /**
     * Storing new user
     * returns user details
     */
	 
	public function newsletter($mobile,$email)
	{
		$result = mysql_query("Update users set email = '$email' where mobile = '$mobile'");
		
		if($result){
			return true;
		}else{
			return false;
		}
	}
	
    public function delivery($mobile, $address) {
        
        $result = mysql_query("INSERT INTO delivery_table(mobile,address) VALUES('$mobile', '$address')");
        // check for successful store
        if ($result) {
            // get user details 
//            $uid = mysql_insert_id(); // last inserted id
            $result = mysql_query("SELECT * FROM delivery_table WHERE mobile = $mobile");
            // return user details
            return mysql_fetch_array($result);
        } else {
            return false;
        }
    }
	public function event($mobile,$location,$date,$time,$event_type,$num_people,$extras)
	{
		$date = date('y-m-d',strtotime($date));
		$time = date("H:i",strtotime($time));
		$numpeople = intval($num_people);
		
		
		$result = mysql_query("INSERT INTO event_table( mobile, location, date, time, event_type, num_people, extras) VALUES('$mobile', '$location', '$date' , '$time', '$event_type', $numpeople, '$extras')");
		
		if ($result) {
            // get user details 
//            $uid = mysql_insert_id(); // last inserted id
            //$result = mysql_query("SELECT * FROM event_table WHERE mobile = $mobile");
            // return user details
            //return mysql_fetch_array($result);
			return true;
        } else {
            return false;
        }
    }
	public function sitting_space($time,$date,$location,$num_people)
	{
		$pre = date("G:i",strtotime($time. '-50 minutes'));
		$fwd = date("G:i",strtotime($time. '+60 minutes'));
	
		$last = "23:59";
		$count = 0;
		$zero = "00:00";
	
		if($time >= "23:00"){
			$res = mysql_query("select * from booktable_table where time between '$pre' and '$last' and date = '$date' and location = '$location'");
			while($row = mysql_fetch_array($res)){
				$count += $row['num_people'];
			}
			$tomorrow = date("Y-m-d", strtotime($date. ' + 1 days'));
			$res2 = mysql_query("select * from booktable_table where time between '$zero' and '$fwd' and date = '$tomorrow' and location = '$location'");
		
			while($row = mysql_fetch_array($res2)){
				$count += $row['num_people'];
			}
		}else if($time < "00:50"){
			$yest = date("Y-m-d", strtotime($date. ' - 1 days'));
			$res = mysql_query("select * from booktable_table where time between '$pre' and '$last' and date = '$yest' and location = '$location'");
			while($row = mysql_fetch_array($res)){
				$count += $row['num_people'];
			}
		
			$res2 = mysql_query("select * from booktable_table where time between '$zero' and '$fwd' and date = '$date' and location = '$location'");
		
			while($row = mysql_fetch_array($res2)){
				$count += $row['num_people'];
			}
		}else{
			$result = mysql_query("select * from booktable_table where time between '$pre' and '$fwd' and date = '$date' and location = '$location'");
			//$row = mysql_fetch_array($result);
			while($row = mysql_fetch_array($result)){
				$count += $row['num_people'];
			}
		}
		$numpeople = intval($num_people);
		if($count + $numpeople > 60){
			return true;
		}else{
			return false;
		}
	}
	
	public function book_table($mobile,$location,$date,$time,$num_people,$extras)
	{
		$date = date('y-m-d',strtotime($date));
		$time = date("G:i",strtotime($time));
		$numpeople = intval($num_people);
		$insert = mysql_query("INSERT INTO booktable_table( mobile, location, date, time, num_people, extras) VALUES('$mobile', '$location', '$date' , '$time', $numpeople, '$extras')");
		
		if ($insert) {
            // get user details 
//            $uid = mysql_insert_id(); // last inserted id
           // $insert = mysql_query("SELECT * FROM booktable_table WHERE mobile = $mobile");
            // return user details
            //return mysql_fetch_array($insert);
			return true;
        } else {
            return false;
        }
    }

	public function food_table($foodname,$quantity,$cost,$type)
	{
		//$date = date('y-m-d',strtotime($date));
		//$time = date("h:i:s",strtotime($time));
		//$numpeople = intval($num_people);
		if ($type == "delivery"){
		
			$result = mysql_query("select * from delivery_table order by food_id desc LIMIT 1");
			
			while($row = mysql_fetch_array($result))
			{
				$food_id = $row['food_id'];
				$food_id = intval($food_id);
			
			$quantity = intval($quantity);
			$cost = intval($cost);
			$result = mysql_query("INSERT INTO food_order VALUES($food_id, '$food_name', $quantity , $cost, '$type')");
			}
			if ($result) {
            // get user details 
//            $uid = mysql_insert_id(); // last inserted id
				$result = mysql_query("SELECT * FROM food_table WHERE food_name = $food_name");
            // return user details
				return mysql_fetch_array($result);
			} else {
				return false;
			}
		}else if($type == "event"){
			$result = mysql_query("select * from event_table order by food_id desc LIMIT 1");
			
			while($row = mysql_fetch_array($result))
			{
				$food_id = $row['food_id'];
				$food_id = intval($food_id);
			
			$quantity = intval($quantity);
			$cost = intval($cost);
			$result = mysql_query("INSERT INTO food_order VALUES($food_id, '$food_name', $quantity , $cost, '$type')");
			}
			if ($result) {
            // get user details 
//            $uid = mysql_insert_id(); // last inserted id
				$result = mysql_query("SELECT * FROM food_table WHERE food_name = $food_name");
            // return user details
				return mysql_fetch_array($result);
			} else {
				return false;
			}
		}else if($type == "book_table"){
			$result = mysql_query("select * from booktable_table order by food_id desc LIMIT 1");
			
			while($row = mysql_fetch_array($result))
			{
				$food_id = $row['food_id'];
				$food_id = intval($food_id);
			
			$quantity = intval($quantity);
			$cost = intval($cost);
			$result = mysql_query("INSERT INTO food_order VALUES($food_id, '$food_name', $quantity , $cost, '$type')");
			}
			if ($result) {
            // get user details 
//            $uid = mysql_insert_id(); // last inserted id
				$result = mysql_query("SELECT * FROM food_table WHERE food_name = $food_name");
            // return user details
				return mysql_fetch_array($result);
			} else {
				return false;
			}
		}
    }
    
}
?>
