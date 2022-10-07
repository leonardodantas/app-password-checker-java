package com.br.app.validation.config.validators;

import com.br.app.validation.app.usecases.impl.steps.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ValidatorsChainBeans {

    @Bean("shouldNotContainRepeatingCharacters")
    public ShouldNotContainRepeatingCharacters shouldNotContainRepeatingCharacters() {
        return new ShouldNotContainRepeatingCharacters();
    }

    @Bean("containsAtLeastOneSpecialCharacter")
    public ContainsAtLeastOneSpecialCharacter containsAtLeastOneSpecialCharacter(final ShouldNotContainRepeatingCharacters shouldNotContainRepeatingCharacters) {
        final var containsAtLeastOneSpecialCharacter = new ContainsAtLeastOneSpecialCharacter();
        containsAtLeastOneSpecialCharacter.next(shouldNotContainRepeatingCharacters);
        return containsAtLeastOneSpecialCharacter;
    }

    @Bean("containsAtLeastOneUppercaseLetter")
    public ContainsAtLeastOneUppercaseLetter containsAtLeastOneUppercaseLetter(final ContainsAtLeastOneSpecialCharacter containsAtLeastOneSpecialCharacter) {
        final var containsAtLeastOneUppercaseLetter = new ContainsAtLeastOneUppercaseLetter();
        containsAtLeastOneUppercaseLetter.next(containsAtLeastOneSpecialCharacter);
        return containsAtLeastOneUppercaseLetter;
    }

    @Bean("containsAtLeastOneLowercaseLetter")
    public ContainsAtLeastOneLowercaseLetter containsAtLeastOneLowercaseLetter(final ContainsAtLeastOneUppercaseLetter containsAtLeastOneUppercaseLetter) {
        final var containsAtLeastOneLowercaseLetter = new ContainsAtLeastOneLowercaseLetter();
        containsAtLeastOneLowercaseLetter.next(containsAtLeastOneUppercaseLetter);
        return containsAtLeastOneLowercaseLetter;
    }

    @Bean("containsAtLeastOneDigit")
    public ContainsAtLeastOneDigit containsAtLeastOneDigit(final ContainsAtLeastOneLowercaseLetter containsAtLeastOneLowercaseLetter) {
        final var containsAtLeastOneDigit = new ContainsAtLeastOneDigit();
        containsAtLeastOneDigit.next(containsAtLeastOneLowercaseLetter);
        return containsAtLeastOneDigit;
    }

    @Primary
    @Bean("characterSize")
    public CharacterSize characterSize(final ContainsAtLeastOneDigit containsAtLeastOneDigit) {
        final var characterSize = new CharacterSize();
        characterSize.next(containsAtLeastOneDigit);
        return characterSize;
    }
}
