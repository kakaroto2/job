#!/bin/bash
nginx -g "daemon off;" &
echo "nginx start :"
jobs
tailf /var/log/nginx/access.log

