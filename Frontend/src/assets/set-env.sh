#!/bin/bash

exec 3<> env.json

    echo "{" >&3
    echo "  \"production\": true," >&3
    echo "  \"authServer\": \"$AUTH_SERVER\"," >&3
    echo "  \"dataAPI\": \"$SEARCH_API\"," >&3
    echo "  \"searchAPI\": \"$DATA_API\"" >&3
    echo "}" >&3

exec 3>&-


