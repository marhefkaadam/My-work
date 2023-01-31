#lang racket

(require "helper.rkt")

; --------------------------------
; Main implementation of semestral - Rule 110 (marheada)
; Tests can be found in "tests.rkt"
; --------------------------------
; Rules for RULE 110
; Current pattern	 111	 110	 101	 100	 011	 010	 001	 000
; New state, center cell  0	  1	  1	  0	  1	  1	  1	  0
; --------------------------------

(define (rule triple)
  (cond
    ((null? triple)                (void))
    ((rule-equal? '(1 1 1) triple) 0)
    ((rule-equal? '(1 1 0) triple) 1)
    ((rule-equal? '(1 0 1) triple) 1)
    ((rule-equal? '(1 0 0) triple) 0)
    ((rule-equal? '(0 1 1) triple) 1)
    ((rule-equal? '(0 1 0) triple) 1)
    ((rule-equal? '(0 0 1) triple) 1)
    ((rule-equal? '(0 0 0) triple) 0)
    (#t                            (void))   ; won't happen
  )
)  

; --------------------------------
; Implementation
; --------------------------------

(define (next-line lst)
  (next-line-aux lst null (car lst) '())
)

(define (next-line-aux lst prev first-el acc)
  (cond
    ((null? lst) acc)

    ((and (null? prev) (null? (cdr lst)))   ; if the list has only one element we will get just one triple
     (let ((triple (list (car lst) (car lst) (car lst))))
       (my-append acc (rule triple))
       ))
    
    ((null? prev)   ; for the triple of first element we need to overflow to the end of the list
     (let ((triple (list (last-el lst) (car lst) (cadr lst))))
       (next-line-aux (cdr lst) (car lst) first-el (my-append acc (rule triple)))
       ))
    
    ((null? (cdr lst))   ; for the triple of the last element we need to overflow to the first element of the list
     (let ((triple (list prev (car lst) first-el)))
       (next-line-aux (cdr lst) (car lst) first-el (my-append acc (rule triple)))
       ))
    (#t
     (let ((triple (list prev (car lst) (cadr lst))))   ; we make triple from the previous, current and next element of list
       (next-line-aux (cdr lst) (car lst) first-el (my-append acc (rule triple)))
       ))
  )
)

; ----- Main function -----
; lst = is the initial list from which the derivations will be made
; times = is how many times should the Rule 110 be applied to the list and it's derivations
(define (rule-110 lst times)
  (rule-110-aux lst times (my-append '() lst))
)

(define (rule-110-aux lst times acc)  
  (cond
    ((null? lst) lst)
    ((= 0 times) acc)
    (#t          (let ((new-line (next-line lst)))
                   (rule-110-aux new-line (- times 1) (my-append acc new-line))
                   ))
  )
)


; --------------------------------
(provide (all-defined-out))