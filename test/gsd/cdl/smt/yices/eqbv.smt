(define str::(record size::int data::(bitvector size)))

(assert (= str (mkrecord 4 0b0000)))

(check)