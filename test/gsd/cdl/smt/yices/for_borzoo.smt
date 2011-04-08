(define-type ENUMERATION_scalar (scalar OPTION_1 OPTION_2 ))

(define BOOL_DATA_OPTION_1_bool_var::bool)

(define BOOL_DATA_OPTION_bool_var::bool)

(define ACIVE_IF_ENUMERATION_data_var::int)

(define OPTION_IMPLEMENTS_INTERFACE_bool_var::bool)

(define BOOL_DATA_OPTION_1_data_var::int)

(define LEGAL_VALUE_IN_RANGE_data_var::int)

(define BOOL_DATA_OPTION_data_var::int)

(define ENUMERATION_scalar_var::ENUMERATION_scalar)

;; . . . . . . . . . . . . . . . . . .
;; ((1:Int <= LEGAL_VALUE_IN_RANGE_data_var) && (LEGAL_VALUE_IN_RANGE_data_var <= 3:Int))

(assert (and (<= 1 LEGAL_VALUE_IN_RANGE_data_var) (<= LEGAL_VALUE_IN_RANGE_data_var 3)))

;; . . . . . . . . . . . . . . . . . .
;; (!((ENUMERATION_scalar_var = enum_OPTION_1) && BOOL_DATA_OPTION_bool_var) || (((ENUMERATION_scalar_var = enum_OPTION_1) ? ACIVE_IF_ENUMERATION_data_var : 0:Int) < 3:Int))

(assert (or (not (and (ENUMERATION_scalar_var OPTION_1) BOOL_DATA_OPTION_bool_var)) (< (if (ENUMERATION_scalar_var OPTION_1) ACIVE_IF_ENUMERATION_data_var 0) 3)))

(check)
