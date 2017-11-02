package kz.gov.pki.osgi.layer.api;

import java.security.Provider;

public interface NCALayerService {
    Provider getProvider();
    void showBundleManager();
    void setUpdateFile(NCALayerJSON updateFile);
}
