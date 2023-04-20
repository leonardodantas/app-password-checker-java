# APP-PASSWORD-CHECKER-JAVA

<p>
Aplicação back-end utilizando Java e Spring Boot que tem como objetivo verificar a validade de senhas com base em uma série de regras. Por meio da implementação de um algoritmo robusto e eficiente, a aplicação é capaz de avaliar a segurança das senhas inseridas pelos usuários e retornar um resultado indicando se a senha é válida ou não.
</p>

### :pushpin: Features

- [x] Verificar senhas.

### :hammer: Pré-requisitos

Será necessário a utilização de uma IDE de sua preferência e do JDK 11.

### 🎲 Iniciando projeto pela primeira vez

```bash
# Clone este repositório
git clone https://github.com/leonardodantas/app-password-checker-java.git

# Inicie a aplicação com uma IDE de sua preferência

#Acesse o seguinte endereço no navegador
http://localhost:8080/swagger-ui/

```

### 🛠 Detalhes Tecnicos

- Java 11
- Arquitetura baseada em Clean Arch
- Criação de Annotations para validação de dados
- Utilização do design patterns Chain of Responsibility
- Swagger
- Banco H2
- Testes unitários e integrados
- BCryptPasswordEncoder

## Documentação da API

- ### Validar se uma senha é valida

```
POST /validation/password
```

A requisição precisa do seguinte body:
| Parâmetro | Tipo | Descrição |
| :---------- | :--------- | :---------------------------------- |
| `password` | `String` | **Obrigatório**. String contendo a senha a ser validada |

CURL de exemplo:

```
curl -X POST "http://localhost:8080/validation/password" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"password\": \"AbTp9!fok\"}"
```

## Desafio

O desafio para esse projeto, é a criação de um serviço rest capaz de verificar se uma string é valida de acordo com os
seguintes critérios:

- Nove ou mais caracteres
- Ao menos 1 dígito
- Ao menos 1 letra minúscula
- Ao menos 1 letra maiúscula
- Ao menos 1 caractere especial: !@#$%^&*()-+
- Não possuir caracteres repetidos dentro do conjunto

Partindo das premissas anteriores adicionei mais uma validação. A de não aceitar request que contenham apenas um valor
vazio " ", adicionei essa validação para deixar o serviço mais rico e apresentar posteriormente mais um tipo de
validação

## Possibilidades para resolver o desafio

Inicialmente pensei em três possibilidades para a resolução do desafio.
A primeira com a **criação de anotações personalizadas**:

<p>
Com SpringBoot temos a possibilidade de criar anotações que podem ser bem uteis para validar a entrada de dados.
Tal conceito já é utilizado pelo BeanValidation, quando por exemplo usamos anotações como @NotNull, @NotBlank, @Positive, etc.
Esse conceito foi empregado em uma validação a mais que eu adicionei no projeto, e pode ser verificada a sua implementação
na @interface InputEmptyAnnotation e a sua empregabilidade pode ser vista na classe PasswordRequest. Nesse caso em especifico
a validação lança a exception MethodArgumentNotValidException que é tratada na classe AdviceController, diferente das outras
validações que a resposta é um booleano true para senha valida e false para uma senha invalida, nesse caso em especifico
utilizei a captura do erro no advice e retornei uma mensagem personalizada como pode ser
conferida abaixo.
</p>

```
{
  "uuid": "9150759d-b67d-4eb0-9e1c-9642a06aeca9",
  "details": [
    {
      "field": "password",
      "message": "Input cannot be empty"
    }
  ],
  "date": "2022-10-08T17:18:28.897911"
}
```

A segunda abordagem é a utilização do método forEach() pelas classes de validação:
<p>
Para resolver o problema com essa solução, é necessário ter uma interface comum para todas as classes de validação. Neste exemplo, usaremos a interface IValidator e implementaremos essa interface para cada validação obrigatória do projeto. Em seguida, declaramos a coleção de IValidator e chamamos o método forEach(). Podemos ver um 
exemplo no código abaixo.
</p>

```
    private final Collection<IValidator> validator;

    public ValidatePassword(final IPasswordRepository passwordRepository, Collection<IValidator> validator) {
        this.passwordRepository = passwordRepository;
        this.validator = validator;
    }

    @Override
    public boolean execute(final Password password) {
        try {
            validator.forEach(valid -> valid.execute(password));
        } catch (final Exception exception) {
            log.error(String.format("Error message: %s", exception.getMessage()));
            return false;
        }
        return savePassword(password);
    }

```

A terceira abordagem que consideramos foi a utilização do design pattern Chain of Responsibility.

<p>
De acordo com o livro "Mergulho nos Padrões de Projetos", de Alexandre Shvets, "Chain of Responsibility é um padrão comportamental que permite que você passe pedidos por uma corrente de handlers. Ao receber um pedido, cada handler decide se processa o pedido ou o passa adiante para o próximo handler na corrente."

Para aplicar esse padrão, precisamos ter vários componentes que executam a validação em etapas e chamam o próximo componente na sequência, e assim sucessivamente. Inicialmente, criamos a interface IValidatorChain, que declara dois métodos.

Essa abordagem permite uma maior flexibilidade na definição da sequência de validação e na adição ou remoção de componentes. No entanto, é necessário tomar cuidado para evitar o acoplamento excessivo entre os componentes e manter uma boa organização na estrutura de classes.
</p>

- **Execute**: Método que irá conter a execução da validação
- **Next**: Método onde conseguiremos atribuir qual será o próximo Bean

<p>
Para definir a sequência de validação e configurar a inicialização dos beans, criei a classe ValidatorsChainBeans. Essa classe foi anotada com @Configuration, o que faz com que o Spring execute-a durante a inicialização do projeto.

Na classe ValidatorsChainBeans, defini cada bean de validação em sua própria função e anotei o primeiro bean que deveria ser executado com @Primary. Dessa forma, o Spring garante que esse bean será o primeiro a ser chamado na sequência de validação.

É importante destacar que a classe ValidatorsChainBeans é uma forma eficiente de organizar a inicialização dos beans e definir a sequência de validação. No entanto, é preciso ter cuidado para manter uma boa organização e evitar o acoplamento excessivo entre os beans.
</p>

```

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
```
<p>
A execução das validações acontecem da seguinte maneira:
</p>

```
    @Override
    public boolean execute(final Password password) {
        try {
            validatorChain.execute(password);
        } catch (final Exception exception) {
            log.error(String.format("Error message: %s", exception.getMessage()));
            return false;
        }
        return savePassword(password);
    }
```

Caso a senha não passe por alguma validação, o cliente receberá um retorno booleano false. Entretanto, podemos verificar o log para saber em qual etapa da validação a validação parou e qual foi a mensagem de erro correspondente.

Consultar o log pode ser útil para entender melhor o problema e, eventualmente, tomar medidas para corrigi-lo. É importante monitorar regularmente o log para identificar rapidamente quaisquer problemas e evitar que afetem o desempenho da aplicação.

## DETALHES DA SOLUÇÃO.
<p>
Para manter as classes do projeto coesas e pequenas, cada validação foi implementada em uma classe separada que implementa uma interface comum a todas elas. Na maioria das vezes, utilizei expressões regulares (Regex) para realizar as validações, mas também empreguei outras técnicas, como a utilização da estrutura de dados Set para verificar a presença de caracteres repetidos. Cada validação possui um log indicando em qual etapa do processo de validação se encontra. Se o input não passar em alguma validação, é lançada uma exception e gerado um log detalhando o erro ocorrido.
</p>

## SOLID, CLEAN ARCH E BOAS PRATICAS DE PROGRAMAÇÃO.
<p>
Para o desenvolvimento do projeto tentei fazer ao máximo o uso de boas práticas. 
A nível de código podemos ver a utilização de alguns princípios do SOLID. 
O single responsibility principle pode ser observado na implementação de classes
coesas, pequenas, e que possuem um unico objetivo para a sua existência, talvez o 
maior exemplo desse principio seja que para cada validação existe uma classe. O
open closed principle pode ser visto na ValidatorsChainBeans, onde para adicionar
um novo tipo de validação, não precisamos nos preocupar com as demais classes,
nossa unica tarefa é criar um novo bean e adicioná-lo na corrente. O Dependency Inversion Principle
pode ser encontrado por exemplo na implementação do repositório, a interface inicial se
encontra na camada de app, esta camada não pode enxergar a camada de infra, e nesse caso
para que a camada de app tenha acesso a camada de infra, utilizamos o princípio de inversão
de dependência. Por ultimo podemos observar a utilização do Interface Segregation Principle,
pois temos interfaces coesas, e quando alguma classe a implementa, fazemos uso de todos
os métodos disponíveis.
</p>

<p>
O projeto possui algumas características de arquitetura limpa, ele é dividido
nas seguintes camadas:
</p>

- **CONFIG**: Camada responsável pelas configurações do projeto, definições de beans e de
documentação
- **INFRA**: Camada que possui a responsabilidade de conversar com o mundo externo, aqui temos os
controllers, implementação da database, tudo o que conversa com o mundo externo, deve ser
colocado nessa camada
- **APP**: Nesta camada encontramos, as nossas exceções de negócio, interfaces de acesso a banco
de dados e os nossos casos de uso, que segundo Uncle Bob, são as nossas regras de negócio
referente a aplicação.
- **DOMAIN**: A camada de domain é a responsável por conter as regras crucias de negocio,
são aquelas regras que sempre existiram na empresa e eram executadas de forma manual. Nesse
caso em especifico ela não possui nenhuma logica do tipo, e é apenas um modelo de como
o banco de dados deve guardar as informações

<p>
Para tornar o projeto mais organizado e fácil de ser mantido, utilizei algumas boas práticas que aprendi ao longo da minha experiência. Adotei o hábito de criar métodos com corpos pequenos e nomes descritivos para facilitar a leitura e compreensão do código. Além disso, busquei abstrair ao máximo a aplicação, permitindo que novas validações possam ser adicionadas facilmente no futuro. Dessa forma, a implementação de novas funcionalidades ou melhorias na aplicação será menos trabalhosa e mais eficiente.
</p>

<p>
Anteriormente, para criar uma nova instância de data no Java, o código era escrito de forma mais verbosa e menos descritiva. Entretanto, com a introdução da API de datas na versão 8 da linguagem, é possível utilizar static factory methods para criar objetos de forma mais clara e concisa. Essa abordagem é citada no livro Java Efetivo como uma alternativa aos construtores públicos, pois permite definir nomes que expliquem claramente como o objeto será criado. Durante o projeto, utilizei essa abordagem para criar classes imutáveis e com nomes explicativos.
</p>

```
 Date now = new Date(2022, 10,8, 10,10,10);;
```

<p>
A partir da versão 8 do Java, com a nova API de datas, podemos criar uma data da seguinte
forma:
</p>

```
 final var now = LocalDateTime.of(2022, 10,8, 10,10,10);
```

<p>
Pelo fato de usarmos imutabilidade não temos métodos na nossa classe de domain que permitem
mudar algum atributo, se algum atributo for alterado, um novo objeto é gerado. No trecho
de código a seguir podemos ver a empregabilidade de static factory e métodos que geram um
novo objeto no lugar de alterar algum atributo.

```
    private Password(final String password) {
        this.password = password;
    }

    private Password(final String password, final boolean isValid, final LocalDateTime create) {
        this.password = password;
        this.isValid = isValid;
        this.create = create;
    }

    public static Password from(final String password) {
        return new Password(password);
    }

    public Password encodePassword() {
        return new Password(new BCryptPasswordEncoder().encode(this.password), true, LocalDateTime.now());
    }
```

</p>

## PROPOSTAS DE MELHORIA

- No lugar de um retorno booleano, poderíamos ter um status code 204 para quando
a senha for valida, e um 400 quando a senha for invalida, com uma mensagem de erro.
- Em um cenário onde nossa aplicação vai ser acessada por diversos serviços, e que cada
serviço possui as suas regras de validação de senha. Poderíamos ter uma tabela de configuração,
onde poderíamos habilitar ou desabilitar a validação, ao gravar no banco de dados poderíamos
ter um campo que especifique qual o valor da aplicação que vai ter acesso, como por
exemplo, DEFAULT, APP_BANK, APP_STORE. Assim ao receber o request para saber se a senha é
valida, poderíamos receber tambem um header que nós dirá qual a configuração que vai ser acessada
no banco de dados, para saber quais serão as validações aceitas para esse cenário.
- Utilização do redis para se trabalhar com cache e evitar que o usuário envie a mesma senha por diversas vezes,
derrubando servidor.

## Apêndice
- Segundo algumas obras da literatura, como o livro Java Efetivo de Joshua Bloch,
exceções não devem ser utilizadas para controle de fluxo. Na verdade a um grande 
debate sobre o tema, e até onde uma exceção deve ser usada para esse propósito, Joshua Bloch diz
que uma exceção deve ser usada apenas em um caso excepcional. A meu ver,
quando paramos em uma validação, uma regra de negocio, faz sentido que uma exceção seja lançada, 
entretanto esse pensamento pode mudar de desenvolvedor para desenvolvedor.
- Se fossemos mais extremistas na utilização de Clean Arch, poderíamos manter a nossa
classe Password que se encontra na camada de Domains sem nenhuma anotação em relação ao banco de dados, sendo apenas
uma classe intermediaria, nesse caso, deveríamos criar uma classe PasswordEntity no package 
database da camada de infra. Entretando isso aumentaria a complexidade do projeto 
de forma desnecessária, teríamos mais classes para lidar e mais converters para criar.
A meu ver a abordagem atual é suficiente.

## Tecnologias

<div style="display: inline_block">
  <img align="center" alt="java" src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white" />
  <img align="center" alt="spring" src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" />
  <img align="center" alt="swagger" src="https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white" />
</div>

### :sunglasses: Autor

Criado por Leonardo Rodrigues Dantas.

[![Linkedin Badge](https://img.shields.io/badge/-Leonardo-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/leonardo-rodrigues-dantas/)](https://www.linkedin.com/in/leonardo-rodrigues-dantas/)
[![Gmail Badge](https://img.shields.io/badge/-leonardordnt1317@gmail.com-c14438?style=flat-square&logo=Gmail&logoColor=white&link=mailto:leonardordnt1317@gmail.com)](mailto:leonardordnt1317@gmail.com)

## Licença

Este projeto esta sobe a licença MIT.
