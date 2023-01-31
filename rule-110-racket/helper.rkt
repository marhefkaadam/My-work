#lang racket

; --------------------------------
; Helpers for semestral - Rule 110 (marheada)
; --------------------------------

; returns last element of the list, if empty -> returns empty list (null)
(define (last-el lst)
  (cond
    ((null? lst) '())
    ((null? (cdr lst)) (car lst))
    (#t     (last-el (cdr lst)))))

; returns new list where the x is appended to the lst
(define (my-append lst x)
  (cond
    ((null? x)   lst)
    ((null? lst) (cons x null))
    (#t          (cons (car lst) (my-append (cdr lst) x)))))

; returns true if the triple of rule is equal to triple of list
; used to determine which derivation of new line should be made for the extracted triple
(define (rule-equal? rule lst)
  (and (equal? (car rule) (car lst))
       (equal? (cadr rule) (cadr lst))
       (equal? (caddr rule) (caddr lst)))
)


; --------------------------------
(provide (all-defined-out))