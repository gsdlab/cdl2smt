;; . . . . . . . . . . . . . . . . . .
;; Translation of the file C:\Users\leonardo\Documents\NetBeansProjects\ece725-project\test\gsd\cdl\smt\yices\enums1.iml

(define-type ENUMERATION_DUPLICATE_scalar (scalar OPTION_1_ENUMERATION_DUPLICATE DATA ))

(define-type ENUMERATION_scalar (scalar OPTION_1 OPTION_2 ))

(define ACIVE_IF_ENUMERATION_DUPLICATE_data_var::int)

(define ENUMERATION_DUPLICATE_scalar_var::ENUMERATION_DUPLICATE_scalar)

(define ENUMERATION_scalar_var::ENUMERATION_scalar)

;; . . . . . . . . . . . . . . . . . .
;; ((enum_OPTION_1 = ENUMERATION_DUPLICATE_scalar_var) || (enum_DATA = ENUMERATION_DUPLICATE_scalar_var))

(assert (or (= ENUMERATION_DUPLICATE_scalar_var OPTION_1_ENUMERATION_DUPLICATE) (= ENUMERATION_DUPLICATE_scalar_var DATA)))

;; . . . . . . . . . . . . . . . . . .
;; ((enum_OPTION_1 = ENUMERATION_scalar_var) || (enum_OPTION_2 = ENUMERATION_scalar_var))

(assert (or (= ENUMERATION_scalar_var OPTION_1) (= ENUMERATION_scalar_var OPTION_2)))

;; . . . . . . . . . . . . . . . . . .
;; (ENUMERATION_scalar_var = enum_OPTION_1)

(assert (= ENUMERATION_scalar_var OPTION_1))

;; . . . . . . . . . . . . . . . . . .
;; (ENUMERATION_DUPLICATE_scalar_var = enum_OPTION_1)

(assert (= ENUMERATION_DUPLICATE_scalar_var OPTION_1_ENUMERATION_DUPLICATE))

(check)
