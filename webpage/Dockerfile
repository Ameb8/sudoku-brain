FROM node:18-alpine
WORKDIR /app

# Access codee and dependencies
COPY package*.json ./
RUN npm install
COPY . .

# Run page
EXPOSE 5173
CMD ["npm", "run", "dev"]
