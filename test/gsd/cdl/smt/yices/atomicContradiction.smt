;; . . . . . . . . . . . . . . . . . .
;; Translation of the file C:\Users\leonardo\Documents\NetBeansProjects\ece725-project\test\gsd\cdl\smt\yices\atomicContradiction.iml

(define-type CYGDAT_IO_SERIAL_FLOW_CONTROL_DEFAULT_scalar (scalar DSRDTR NONE XONXOFF RTSCTS ))

(define CYGPKG_IO_SERIAL_FLOW_CONTROL_bool_var::bool)

(define CYGDAT_IO_SERIAL_FLOW_CONTROL_DEFAULT_scalar_var::CYGDAT_IO_SERIAL_FLOW_CONTROL_DEFAULT_scalar)

;; . . . . . . . . . . . . . . . . . .
;; (!CYGPKG_IO_SERIAL_FLOW_CONTROL_bool_var || ((((enum_NONE = (CYGPKG_IO_SERIAL_FLOW_CONTROL_bool_var ? CYGDAT_IO_SERIAL_FLOW_CONTROL_DEFAULT_scalar_var : false)) || (enum_XONXOFF = (CYGPKG_IO_SERIAL_FLOW_CONTROL_bool_var ? CYGDAT_IO_SERIAL_FLOW_CONTROL_DEFAULT_scalar_var : false))) || (enum_RTSCTS = (CYGPKG_IO_SERIAL_FLOW_CONTROL_bool_var ? CYGDAT_IO_SERIAL_FLOW_CONTROL_DEFAULT_scalar_var : false))) || (enum_DSRDTR = (CYGPKG_IO_SERIAL_FLOW_CONTROL_bool_var ? CYGDAT_IO_SERIAL_FLOW_CONTROL_DEFAULT_scalar_var : false))))

(assert (or (not CYGPKG_IO_SERIAL_FLOW_CONTROL_bool_var) (or (or (or (= NONE (if CYGPKG_IO_SERIAL_FLOW_CONTROL_bool_var CYGDAT_IO_SERIAL_FLOW_CONTROL_DEFAULT_scalar_var false)) (= XONXOFF (if CYGPKG_IO_SERIAL_FLOW_CONTROL_bool_var CYGDAT_IO_SERIAL_FLOW_CONTROL_DEFAULT_scalar_var false))) (= RTSCTS (if CYGPKG_IO_SERIAL_FLOW_CONTROL_bool_var CYGDAT_IO_SERIAL_FLOW_CONTROL_DEFAULT_scalar_var false))) (= DSRDTR (if CYGPKG_IO_SERIAL_FLOW_CONTROL_bool_var CYGDAT_IO_SERIAL_FLOW_CONTROL_DEFAULT_scalar_var false)))))

;; . . . . . . . . . . . . . . . . . .
;; CYGPKG_IO_SERIAL_FLOW_CONTROL_bool_var

(assert CYGPKG_IO_SERIAL_FLOW_CONTROL_bool_var)

(check)
