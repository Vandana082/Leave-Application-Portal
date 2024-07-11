FROM node:20 AS build
COPY package.json .
COPY package-lock.json .
RUN npm install
COPY . .
COPY ./src/images ./src/images
RUN npm run build
EXPOSE 3000
CMD ["npm", "start"]