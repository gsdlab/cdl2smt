;; . . . . . . . . . . . . . . . . . .
;; Translation of the file C:\Users\leonardo\Documents\NetBeansProjects\ece725-project\test\gsd\cdl\smt\yices\sample2.iml

(define TEST2_bool_var::bool)

(define TEST2_data_var::(bitvector 24))

(define TEST1_bool_var::bool)

;; . . . . . . . . . . . . . . . . . .
;; (!TEST1_bool_var || (TEST2_bool_var ? (TEST2_data_var s-contains ""libbb.so"") : false))

(assert (or (not TEST1_bool_var) (if TEST2_bool_var (= TEST2_data_var 0b100010110110110001101011) false)))

(check)
