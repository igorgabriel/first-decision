# Use a imagem Node para desenvolvimento
FROM node:18

# Defina o diretório de trabalho
WORKDIR /app

# Copie package.json e package-lock.json e instale dependências
COPY package*.json ./
RUN npm install

# Copie todo o código fonte
COPY . .

# Instale o Angular CLI globalmente se necessário
RUN npm install -g @angular/cli

# Exponha a porta padrão do ng serve
EXPOSE 4200

# Comando para iniciar o servidor de dev e abrir o navegador
CMD ["ng", "serve", "--host", "0.0.0.0", "--open"]
