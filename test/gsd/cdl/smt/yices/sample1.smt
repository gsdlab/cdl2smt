;; . . . . . . . . . . . . . . . . . .
;; Translation of the file C:\Users\leonardo\Documents\NetBeansProjects\ece725-project\test\gsd\cdl\smt\yices\sample1.iml

(define OPTION_data_var::(bitvector 0))

(define TEST1_bool_var::bool)

;; . . . . . . . . . . . . . . . . . .
;; (!TEST1_bool_var || (!bool(OPTION_data_var) ? false : false))

(assert (or (not TEST1_bool_var) (if (not (bv-gt OPTION_data_var 0b)) false false)))

(check)
