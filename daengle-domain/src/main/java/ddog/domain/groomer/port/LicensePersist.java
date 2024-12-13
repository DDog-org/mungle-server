package ddog.domain.groomer.port;

import ddog.domain.groomer.License;

public interface LicensePersist {

    License save(License license);
}
