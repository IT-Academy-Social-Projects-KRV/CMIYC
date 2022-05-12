#!/bin/bash

foo=$(cat <<EOF
{
  "production": true,
  "authServer": "$AUTH_SERVER",
  "dataAPI": "$SEARCH_API",
  "searchAPI": "$DATA_API"
}
EOF
)

echo $foo > /usr/share/nginx/html/assets/env.json
