;; . . . . . . . . . . . . . . . . . .
;; Translation of the file C:\Users\leonardo\Documents\NetBeansProjects\ece725-project\test\gsd\cdl\smt\yices\booldataEnums.iml

(define-type ROM_MONITOR_scalar (scalar Generic GDB_stubs ))

(define-type STARTUP_scalar (scalar FLOPPY ROM RAM GRUB ))

(define ROM_MONITOR_scalar_var::ROM_MONITOR_scalar)

(define STARTUP_scalar_var::STARTUP_scalar)

(define ROM_MONITOR_bool_var::bool)

;; . . . . . . . . . . . . . . . . . .
;; (!ROM_MONITOR_bool_var || ((enum_Generic = (ROM_MONITOR_bool_var ? ROM_MONITOR_scalar_var : 0:Int)) || (enum_GDB_stubs = (ROM_MONITOR_bool_var ? ROM_MONITOR_scalar_var : 0:Int))))

(assert (or (not ROM_MONITOR_bool_var) (or (= Generic (if ROM_MONITOR_bool_var ROM_MONITOR_scalar_var 0)) (= GDB_stubs (if ROM_MONITOR_bool_var ROM_MONITOR_scalar_var 0)))))

;; . . . . . . . . . . . . . . . . . .
;; ((((enum_RAM = STARTUP_scalar_var) || (enum_FLOPPY = STARTUP_scalar_var)) || (enum_ROM = STARTUP_scalar_var)) || (enum_GRUB = STARTUP_scalar_var))

(assert (or (or (or (= STARTUP_scalar_var RAM) (= STARTUP_scalar_var FLOPPY)) (= STARTUP_scalar_var ROM)) (= STARTUP_scalar_var GRUB)))

;; . . . . . . . . . . . . . . . . . .
;; (!ROM_MONITOR_bool_var || (STARTUP_scalar_var = enum_RAM))

(assert (or (not ROM_MONITOR_bool_var) (= STARTUP_scalar_var RAM)))

(check)
