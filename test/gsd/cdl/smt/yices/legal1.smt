;; . . . . . . . . . . . . . . . . . .
;; Translation of the file C:\Users\leonardo\Documents\NetBeansProjects\ece725-project\test\gsd\cdl\smt\yices\legal1.iml

(define COMPONENT_bool_var::bool)

(define O2_data_var::int)

(define O1_data_var::int)

;; . . . . . . . . . . . . . . . . . .
;; (!COMPONENT_bool_var || ((16:Int <= (COMPONENT_bool_var ? O2_data_var : 0:Int)) && ((COMPONENT_bool_var ? O2_data_var : 0:Int) <= (bool((COMPONENT_bool_var ? O1_data_var : 0:Int)) ? (COMPONENT_bool_var ? O1_data_var : 0:Int) : 128:Int))))

(assert (or (not COMPONENT_bool_var) (and (<= 16 (if COMPONENT_bool_var O2_data_var 0)) (<= (if COMPONENT_bool_var O2_data_var 0) (if (> (if COMPONENT_bool_var O1_data_var 0) 0) (if COMPONENT_bool_var O1_data_var 0) 128)))))

(check)
