#!/bin/bash
set -e
chown -R jetty:jetty /risetekauth
## launch jetty
exec /docker-entrypoint.sh "$@"

