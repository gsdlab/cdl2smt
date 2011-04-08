(define-type str (datatype (nonEmptyList head::int tail::str) emptyList))




(define name::str (nonEmptyList 2 emptyList))
(define name1::str (nonEmptyList 2 (nonEmptyList 3 emptyList)))
(define list1::str)



; (assert (and (nonEmptyList? name1) (and (= 3 (head (tail name1))) (= 2 (head name1)))))






; (assert (and (= h (head (tail name1))) (= h1 (head name1))))

(assert (and (nonEmptyList? list1) (= 2 (head list1))))
























; ======================================================================

; (define-type intString (subrange 0 4))


(define x::int)
(define y::int)

(define emptyStr::str emptyList)

; (define name1::str (nonEmptyList 2 (nonEmptyList 3 emptyList)))

;(assert (= name1 name))

(define h::int)
(define h1::int)
; (assert (and (and (= h1 3) (= h1 (head (tail name1)))) (and (= h 2) (= h (head name1)))))


;(assert (= 2 (head list1)))

; (assert (/= emptyStr name))

; (assert (= (head name1) (head name)))
; (assert (>= x 10))


; (define strA::str)

; -----------------------------------

(check)
