;; . . . . . . . . . . . . . . . . . .
;; Translation of the file C:\Users\leonardo\Documents\NetBeansProjects\ece725-project\test\gsd\cdl\smt\yices\sample4.iml

(define TEST2_bool_var::bool)

(define TEST3_bool_var::bool)

(define TEST2_data_var::(bitvector 30))

(define TEST1_bool_var::bool)

;; . . . . . . . . . . . . . . . . . .
;; (!TEST3_bool_var || (TEST2_bool_var ? (TEST2_data_var s-contains ""libbxxx.so"") : false))

(assert (or (not TEST3_bool_var) (if TEST2_bool_var (= TEST2_data_var 0b100010110110001001001101011111) false)))

;; . . . . . . . . . . . . . . . . . .
;; (!TEST1_bool_var || (TEST2_bool_var ? (TEST2_data_var s-contains ""libbb.so"") : false))

(assert (or (not TEST1_bool_var) (if TEST2_bool_var 
  (and
    (bv-gt
      TEST2_data_var
      0b100010110110110101011111000000
    )
    (or 
      (=
        (bv-extract 23 0 TEST2_data_var)
        0b100010110110110101011111
      )
      (=
        (bv-extract 26 3 TEST2_data_var)
        0b100010110110110101011111
      )
      (=
        (bv-extract 29 6 TEST2_data_var)
        0b100010110110110101011111
      )
    )
  )
 false)))

(check)
