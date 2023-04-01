package bo.edu.ucb.currencykt.utils

import org.keycloak.KeycloakSecurityContext
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

class KeycloakSecurityContextHolder {

    companion object {

        private fun getKeycloakSecurityContext(): KeycloakSecurityContext? {
            val requestAttributes = RequestContextHolder.currentRequestAttributes() as? ServletRequestAttributes
            return requestAttributes?.request?.getAttribute(KeycloakSecurityContext::class.java.name) as? KeycloakSecurityContext
        }

        fun getEmail(): String? {
            return getKeycloakSecurityContext()?.token?.email
        }

        fun getFullName(): String? {
            return getKeycloakSecurityContext()?.token?.name
        }


    }
}
