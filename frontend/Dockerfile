FROM node:22-alpine AS build
WORKDIR /app
COPY package.json ./
COPY yarn.lock ./
COPY .yarnrc.yml ./
COPY .yarn ./.yarn
RUN corepack enable
RUN yarn install
COPY . .
RUN yarn build

FROM node:22-alpine
WORKDIR /app
COPY --from=build /app/.next ./.next
COPY --from=build /app/node_modules ./node_modules
COPY --from=build /app/package.json ./package.json
COPY --from=build /app/public ./public
COPY --from=build /app/yarn.lock ./yarn.lock
COPY --from=build /app/.yarnrc.yml ./.yarnrc.yml
COPY --from=build /app/.yarn ./.yarn
EXPOSE 3000
RUN corepack enable
CMD ["yarn", "start"]