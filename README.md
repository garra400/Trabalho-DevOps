# 🛠️ Estruturação Inicial do Software

Projeto destinado à definição da arquitetura, organização de pastas, mapeamento de processos e padronização de modificações, seguindo a metodologia **Kanban** para gestão de tarefas e **Git Flow** para controle de versão.

---

## ✨ Status do Projeto

**📋 EM PLANEJAMENTO** – Estrutura inicial e processos sendo definidos.

* ✅ **Metodologia Kanban** configurada para acompanhamento visual de tarefas
* ✅ **Fluxo Git Flow** definido para controle de branches e versões
* ⏳ **Mapeamento de processos** em andamento
* ⏳ **Arquitetura de pastas** em definição
* ⏳ **Padronização de commits e código** pendente de aprovação

---

## 🚀 Objetivos

* Garantir que a estrutura do software seja clara e organizada
* Facilitar a colaboração entre desenvolvedores
* Definir padrões para modificações e criação de novas funcionalidades
* Mapear processos e documentar requisitos
* Manter um fluxo de desenvolvimento contínuo e rastreável

---

## 📦 Estrutura do Projeto (Proposta Inicial)

```
project-name/
├── docs/                  # Documentação do projeto
├── src/                   # Código-fonte principal
│   ├── modules/           # Módulos/funções separados
│   ├── services/          # Integrações e APIs
│   ├── utils/             # Funções auxiliares
│   └── assets/            # Arquivos estáticos (imagens, ícones)
├── tests/                 # Testes automatizados
├── .gitignore             # Arquivos a serem ignorados pelo Git
├── README.md              # Documentação principal
└── LICENSE                # Licença do projeto
```

---

## 📋 Mapeamento de Processos

O mapeamento inclui:

1. **Levantamento de Funcionalidades Necessárias**
2. **Definição de Fluxos Internos**
3. **Criação de Backlog Inicial**
4. **Divisão de Etapas no Kanban**

---

## 📌 Workflow Utilizado

### Kanban

* Quadro dividido em **To Do**, **In Progress**, **Review** e **Done**
* Tarefas registradas como *issues* no repositório
* Uso de *labels* para classificação (bug, feature, enhancement, documentation)

### Git Flow

* Branches principais:

  * `main`: versão estável e de produção
  * `develop`: versão em desenvolvimento
* Branches auxiliares:

  * `feature/*`: novas funcionalidades
  * `release/*`: preparação de releases
  * `hotfix/*`: correções urgentes em produção

---

## ✅ Critérios de Aceitação

* Estrutura de pastas documentada e aprovada
* Fluxo Kanban e Git Flow integrados no repositório
* Funcionalidades mapeadas e priorizadas
* Guia de contribuição definido (commits, PRs e revisão de código)

---

## 📝 Contribuição

1. **Crie uma branch** a partir de `develop`:

   ```bash
   git checkout develop
   git pull origin develop
   git checkout -b feature/nome-da-feature
   ```
2. **Implemente sua alteração**
3. **Faça commit seguindo o padrão**:

   ```
   feat: descrição breve da funcionalidade
   fix: descrição breve da correção
   docs: atualização de documentação
   ```
4. **Abra um Pull Request** para `develop`

---

Se quiser, posso também preparar **uma versão Kanban-friendly para GitHub** com *checklists* e tarefas já organizadas para aparecer no quadro automaticamente, para que você só copie e cole no *issue* principal. Isso já deixaria pronto para você começar a trabalhar no fluxo. Quer que eu faça essa versão também?
