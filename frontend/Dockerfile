 #!/bin/bash
FROM node:14-alpine
WORKDIR /app
COPY . .
RUN npm i
RUN npm run build

CMD ["npm", "run", "start"]