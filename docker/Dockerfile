FROM jetty

COPY src/ROOT.war /var/lib/jetty/webapps/ROOT.war
COPY src/auth-entrypoint.sh /auth-entrypoint.sh

RUN set -xe \
    && mkdir /risetekauth 
    
VOLUME /var/lib/jetty/webapps
VOLUME /risetekauth

## chown /risetekauth and launch jetty
ENTRYPOINT ["/auth-entrypoint.sh"]
