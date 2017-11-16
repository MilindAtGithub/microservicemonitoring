#!/bin/bash
x=1
while [ $x -le 50 ]
do
  echo "$x times"
  curl -H "Content-Type: application/json" -X POST -d '{"username":"xyz","password":"xyz"}' http://localhost:9099/postjson
  x=$(( $x + 1 ))
done
