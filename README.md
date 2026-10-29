# Endless Mutation: Cailee (Minecraft 1.8.9 Forge)

Projeto inicial do mod para **Minecraft Java 1.8.9**, **Forge 11.15.1.2318** e **Java 8**.

## O que está implementado nesta base
- Entidade `EntityCailee` registrada no Forge.
- NPC invulnerável e persistente contra despawn normal.
- Busca por entidades da classe `EntityAnimal`, perseguição e ataque.
- Recompensa de evolução quando o ataque da NPC elimina diretamente a presa.
- Energia, massa muscular, nível, fome e temporizadores persistidos por NBT.
- Modelo articulado básico com vestido rosa e cabelo castanho estilizado.
- Escala visual derivada de fórmula logarítmica; o limite visual não limita a energia lógica.
- Item invocador: clique direito para gerar a NPC.
- Configuração Forge em `config/endlessmutationcailee.cfg`.
- Workflow GitHub Actions para gerar e publicar o JAR como artefato.

## Requisitos
- Minecraft Java Edition 1.8.9.
- Minecraft Forge 11.15.1.2318.
- Java 8 para o ambiente legado de compilação.

## Invocar
Após compilar e instalar o JAR na pasta `mods`, entre em um mundo Forge 1.8.9. O item invocador é registrado como `cailee_summoner`; dependendo do modo de jogo, obtenha-o via inventário criativo ou comando de give. A interação com a NPC mostra os dados básicos no chat.

## Compilar
O projeto usa ForgeGradle 2.1 e mappings `stable_22`, apropriados para o ambiente legado. ForgeGradle antigo pode depender de repositórios e serviços legados que podem ficar indisponíveis.

Se tiver Gradle 2.7 e Java 8 instalados:
1. Abra um terminal na pasta do projeto.
2. Execute `gradle setupDecompWorkspace`.
3. Execute `gradle build`.
4. Procure o JAR em `build/libs/`.

O workflow em `.github/workflows/build.yml` tenta compilar e publica o JAR em **Actions → execução do workflow → Artifacts**. Como ForgeGradle 2.1 é antigo, pode ser necessário ajustar o workflow/ambiente se a distribuição legada falhar.

## Limitações conhecidas e honestidade técnica
- Este projeto foi gerado como uma base inicial e **não foi compilado nem testado dentro do Minecraft neste ambiente**. Não trate o build como validado até uma execução bem-sucedida.
- A renderização usa partes cúbicas do `ModelRenderer`; não é uma malha humana realista. O recurso de textura precisa existir no caminho indicado para exibir uma aparência final completa.
- A evolução lógica não tem um teto de nível configurado, mas `double`, os atributos internos do Minecraft, a precisão numérica e os limites de renderização são finitos.
- O controle de consumo usa o identificador de entidade como proteção simples contra duplicação durante a vida atual da entidade; IDs de entidade podem ser reutilizados após recarregamentos, então um registro persistente por UUID seria mais robusto para cenários extremos.
- A caça atual cobre `EntityAnimal`; classes que não herdam dessa classe podem não ser elegíveis. Teste domesticados e variantes específicas na versão-alvo.
- A IA é uma implementação inicial, não uma simulação avançada de alimentação: validar comportamento em jogo é obrigatório.
- A textura `textures/entity/cailee.png` e o ícone `textures/items/cailee_summoner.png` são recursos esperados, mas ainda não estão incluídos nesta base.

## Testes manuais
1. Compilar e iniciar Minecraft Forge 1.8.9 com o mod.
2. Verificar o registro do mod e ausência de crash no carregamento.
3. Invocar a NPC com o item.
4. Aproximar vaca/porco/ovelha e verificar perseguição e combate.
5. Confirmar que energia e massa só mudam após uma eliminação atribuída à NPC.
6. Fechar e reabrir o mundo e verificar persistência NBT.
7. Aplicar dano, fogo, lava e queda; confirmar invulnerabilidade.
8. Verificar que tempo decorrido sozinho não aumenta energia nem massa.
9. Inspecionar `latest.log` e os logs do GitHub Actions em caso de falha.

## Licença
MIT — veja `LICENSE`.
