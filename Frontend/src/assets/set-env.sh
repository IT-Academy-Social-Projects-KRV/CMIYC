#!/bin/bash

echo "{
  \"production\": true,
  \"authServer\": \"$AUTH_SERVER\",
  \"dataAPI\": \"$SEARCH_API\",
  \"searchAPI\": \"$DATA_API\"
}" > /usr/share/nginx/html/assets/env.json
