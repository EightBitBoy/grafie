package de.eightbitboy.grafie

import spock.lang.Specification

class GrafieTest extends Specification {
    def "run a simple test"() {
        setup:
        def numbers = [1, 2]

        when:
        numbers.add(3)

        then:
        numbers.size() == 3
    }
}
