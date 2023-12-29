# ./deploy.sh
if [ $(docker ps | grep -c "meommu-db") -eq 0 ]; then
  echo "### Starting database ###"
  docker-compose up -d meommu-db
else
  echo "db is already running"
fi

echo

IS_GREEN=$(docker ps | grep green)
DEFAULT_CONF=" /etc/nginx/nginx.conf"

if [ -z $IS_GREEN  ];then # BLUE is running

  echo "### BLUE => GREEN ###"

  echo "1. get green image"
  docker-compose pull green

  echo "2. green container up"
  docker-compose up -d green

  while [ 1 = 1 ]; do
  echo "3. green health check..."
  sleep 3

  REQUEST=$(curl http://127.0.0.1:8082) # GREEN Health Check
    if [ -n "$REQUEST" ]; then
            echo "health check success"
            break ;
            fi
  done;

  echo "4. reload nginx"
  sudo cp /etc/nginx/nginx.green.conf /etc/nginx/nginx.conf
  sudo nginx -s reload

  echo "5. blue container down"
  docker-compose stop blue

else # GREEN is running
  echo "### GREEN => BLUE ###"

  echo "1. get blue image"
  docker-compose pull blue

  echo "2. blue container up"
  docker-compose up -d blue

  while [ 1 = 1 ]; do
    echo "3. blue health check..."
    sleep 3
    REQUEST=$(curl http://127.0.0.1:8081) # BLUE Health Check

    if [ -n "$REQUEST" ]; then
      echo "health check success"
      break ;
    fi
  done;

  echo "4. reload nginx"
  sudo cp /etc/nginx/nginx.blue.conf /etc/nginx/nginx.conf
  sudo nginx -s reload

  echo "5. green container down"
  docker-compose stop green
fi