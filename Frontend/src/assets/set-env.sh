#!/bin/bash

echo "{
  \"production\": true,
  \"authServer\": \"$AUTH_SERVER\",
  \"dataAPI\": \"$DATA_API\",
  \"searchAPI\": \"$SEARCH_API\"
}" > /usr/share/nginx/html/assets/env.json
