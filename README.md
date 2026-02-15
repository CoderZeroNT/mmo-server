# 🚀 Project Roadmap

## 🧱 1. Backend Foundation

### 🔐 Authentication

* [x] Criar projeto Spring Boot
* [x] Configurar MongoDB
* [x] Configurar Redis 
* [x] Criar entidade `User`
* [x] Criar entidade `Player`
* [x] Implementar BCrypt
* [x] Criar endpoint `/register`
* [x] Criar endpoint `/login`
* [x] Implementar JWT
* [x] Validar JWT no WebSocket

### 🌐 WebSocket Core

* [ ] Criar endpoint `/ws/game`
* [ ] Criar sistema de mensagens padrão
* [ ] Criar enum `GameEvent`
* [ ] Implementar evento CONNECT
* [ ] Implementar evento DISCONNECT
* [ ] Implementar evento MOVE
* [ ] Implementar evento ATTACK
* [ ] Implementar evento CHAT
* [ ] Implementar evento USE_SKILL

### 🧠 Game Loop

* [ ] Criar `WorldService`
* [ ] Criar loop com `ScheduledExecutorService`
* [ ] Atualizar mobs no loop
* [ ] Processar combate no loop
* [ ] Processar cooldowns
* [ ] Broadcast de estado

---

## 🗺️ 2. World & Map System

### 🧱 Estrutura do Mundo

* [ ] Criar classe `World`
* [ ] Criar sistema de zonas/regiões
* [ ] Criar sistema de spawn inicial

### 🧭 Mapa

* [ ] Criar mapa no Tiled
* [ ] Exportar JSON
* [ ] Implementar colisão server-side
* [ ] Validar movimento no servidor
* [ ] Implementar sistema de limite de mapa

---

## 🎮 3. Frontend Foundation

### ⚛ React Setup

* [ ] Criar projeto React
* [ ] Instalar Phaser
* [ ] Criar componente `<Game />`
* [ ] Integrar Phaser ao React

### 🧍 Player Rendering

* [ ] Renderizar player local
* [ ] Renderizar outros jogadores
* [ ] Mostrar nome acima do player
* [ ] Mostrar barra de HP
* [ ] Mostrar barra de XP

### 🏃 Movement

* [ ] Capturar input (WASD)
* [ ] Enviar posição ao servidor
* [ ] Implementar interpolação
* [ ] Atualizar posição de outros jogadores

---

## ⚔️ 4. Combat System

### 🗡 Sistema Base

* [ ] Criar atributo `attack`
* [ ] Criar atributo `defense`
* [ ] Criar cálculo de dano
* [ ] Validar range de ataque
* [ ] Implementar cooldown
* [ ] Sincronizar HP

### 🧠 Server Authority

* [ ] Impedir dano calculado no client
* [ ] Validar velocidade de ataque
* [ ] Validar distância de ataque

---

## 📈 5. Level System

* [ ] Criar atributo `level`
* [ ] Criar atributo `xp`
* [ ] Criar fórmula XP necessária
* [ ] Implementar level up
* [ ] Aumentar atributos ao subir level
* [ ] Salvar no banco

---

## 🏹 6. Skill System

### ⚔ Skills

* [ ] Sword
* [ ] Distance
* [ ] Magic
* [ ] Defense
* [ ] Vitality

### 📊 Skill Mechanics

* [ ] Criar classe `Skills`
* [ ] Implementar XP por uso
* [ ] Criar fórmula de progressão
* [ ] Atualizar dano baseado na skill
* [ ] Atualizar HP baseado em Vitality
* [ ] Enviar atualização de skill ao client

### 🖥 UI Skills

* [ ] Criar painel de skills
* [ ] Mostrar level da skill
* [ ] Mostrar progresso %
* [ ] Atualizar em tempo real

---

## 👾 7. Mob System

### 🧟 Estrutura

* [ ] Criar classe `Mob`
* [ ] Criar spawn automático
* [ ] Salvar mobs no Redis
* [ ] Broadcast spawn

### 🤖 IA

* [ ] Detectar player próximo
* [ ] Atacar player
* [ ] Idle quando longe
* [ ] Implementar respawn

---

## 🎒 8. Inventory & Items

### 🎒 Inventário

* [ ] Criar classe `Item`
* [ ] Criar limite de slots
* [ ] Adicionar loot ao matar mob
* [ ] Enviar inventário ao client

### 🛡 Equipamento

* [ ] Criar slot Weapon
* [ ] Criar slot Armor
* [ ] Aplicar bônus ao equipar
* [ ] Atualizar stats dinamicamente

---

## 💬 9. Chat System

* [ ] Implementar chat global
* [ ] Broadcast mensagens
* [ ] Criar UI de chat
* [ ] Limitar tamanho de mensagem
* [ ] Anti-spam básico

---

## ☠️ 10. Death System

* [ ] Detectar HP <= 0
* [ ] Respawn em cidade
* [ ] Restaurar HP
* [ ] Perder XP %
* [ ] Salvar estado

---

## 🛡 11. Anti-Cheat

* [ ] Validar velocidade de movimento
* [ ] Validar ataque fora de range
* [ ] Validar spam de eventos
* [ ] Rate limit WebSocket
* [ ] Logar comportamento suspeito

---

## ⚡ 12. Performance

* [ ] Não enviar posição todo frame
* [ ] Implementar delta update
* [ ] Implementar sistema de zonas
* [ ] Usar Redis para estado online
* [ ] Salvar no Mongo apenas eventos importantes

---

## 💾 13. Persistence

* [ ] Salvar ao desconectar
* [ ] Salvar ao subir level
* [ ] Salvar ao equipar item
* [ ] Salvar ao morrer
* [ ] Backup básico do banco

---

## 🎨 14. Polish

* [ ] Animação de ataque
* [ ] Efeitos visuais simples
* [ ] Sons básicos
* [ ] Melhorar UI
* [ ] Tela de loading
* [ ] Tela de login estilizada

---

## 🧪 15. Testing

* [ ] Testar 2 jogadores simultâneos
* [ ] Testar 10 jogadores simultâneos
* [ ] Testar respawn
* [ ] Testar perda de conexão
* [ ] Testar reconexão

---

## 📦 16. Deploy

* [ ] Configurar variáveis de ambiente
* [ ] Dockerizar backend
* [ ] Configurar Mongo produção
* [ ] Configurar Redis produção
* [ ] Deploy backend
* [ ] Deploy frontend
* [ ] Testar ambiente real

---

## 🏆 MVP Final Check

* [ ] Multiplayer funcional
* [ ] Combate funcional
* [ ] Skills funcionando
* [ ] Level funcionando
* [ ] Mobs ativos
* [ ] Loot funcionando
* [ ] Inventário funcional
* [ ] Chat funcional
* [ ] Persistência funcionando
* [ ] Anti-cheat básico ativo

---

## 📈 Fase 2 (Após MVP)

* [ ] PvP
* [ ] Party system
* [ ] Guild system
* [ ] Trade system
* [ ] Market
* [ ] Dungeons
* [ ] Bosses
* [ ] Sistema de quests
* [ ] Pets
* [ ] Montaria terrestre e aquatica
* [ ] 
* [ ] Economia balanceada
* [ ] Logs administrativos
* [ ] Painel GM