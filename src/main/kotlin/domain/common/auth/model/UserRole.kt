package domain.common.auth.model

/**
 * Enum class representing the different roles a user can have in the system.
 *
 * These roles are used for authorization and access control throughout the application.
 */
enum class UserRole {
    /** Standard user role with permission to browse, order, and manage personal data. */
    USER,

    /** Seller role with permissions to manage products and view related orders. */
    SELLER,

    /** Administrator role with full access to all system data and operations. */
    ADMIN
}
