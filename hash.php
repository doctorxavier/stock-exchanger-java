<?php 

	$url="https://api.kraken.com";
	$version = 0;
	$method = "Balance";
	$key = "";
	$secret = "";
	
	$curl = curl_init();
	curl_setopt_array($curl, array(
			CURLOPT_SSL_VERIFYPEER => true,
			CURLOPT_SSL_VERIFYHOST => 2,
			CURLOPT_USERAGENT => 'Kraken PHP API Agent',
			CURLOPT_POST => true,
			CURLOPT_RETURNTRANSFER => true)
		);
	
	if(!isset($request['nonce'])) {
            // generate a 64 bit nonce using a timestamp at microsecond resolution
            // string functions are used to avoid problems on 32 bit systems
        $nonce = explode(' ', microtime());
        $request['nonce'] = $nonce[1] . str_pad(substr($nonce[0], 2, 6), 6, '0');
		echo $nonce[0] . "\n";
		echo $nonce[1] . "\n";
		echo $request['nonce'] . "\n";
	}
	
	// $request['nonce'] = 1408248726657644;
	
	// build the POST data string
    $postdata = http_build_query($request, '', '&');

    // set API key and sign the message
    $path = '/' . $version . '/private/' . $method;
    $sign = hash_hmac('sha512', $path . hash('sha256', $request['nonce'] . $postdata, true), base64_decode($secret), true);
    $headers = array(
        'API-Key: ' . $key,
        'API-Sign: ' . base64_encode($sign)
    );
	
	echo $headers[0] . "\n";
	echo $headers[1] . "\n";
	
	// make request
	curl_setopt($curl, CURLOPT_URL, $url . $path);
    curl_setopt($curl, CURLOPT_POSTFIELDS, $postdata);
    curl_setopt($curl, CURLOPT_HTTPHEADER, $headers);
    $result = curl_exec($curl);
    if($result===false)
       throw new KrakenAPIException('CURL error: ' . curl_error($curl));
    
	// decode results
	$result = json_decode($result, true);
	if(!is_array($result))
		throw new KrakenAPIException('JSON decode error');

	echo "ZEUR: " . $result["result"]["ZEUR"] . "\n";
	echo "XXBT: " . $result["result"]["XXBT"] . "\n";
	echo "error: " . $result["error"][0] . "\n";
	
	// echo $result[3][X] . "\n";
	
	curl_close($curl);
	
?>