# Образ для запуска приложения
FROM microsoft/dotnet:2.1-aspnetcore-runtime AS base
WORKDIR /app
ENV ASPNETCORE_URLS http://*:8383
ENV ASPNETCORE_ENVIRONMENT Production
EXPOSE 8383

# Образ для сборки приложения
FROM microsoft/dotnet:2.1-sdk AS build
WORKDIR /src
COPY ["AlphaParking.Web.Client.csproj", "AlphaParking.Web.Client/"]
WORKDIR /src/AlphaParking.Web.Client
RUN dotnet restore "AlphaParking.Web.Client.csproj"
COPY . .
RUN dotnet build "AlphaParking.Web.Client.csproj" -c Release -o /app

# Образ для сборки фронта
FROM node:8.11 as node-build
WORKDIR /web
COPY . .
RUN npm install
RUN npm rebuild node-sass
RUN npm run build

FROM build AS publish
WORKDIR /src/AlphaParking.Web.Client/wwwroot
COPY --from=node-build /web/wwwroot .
WORKDIR /src/AlphaParking.Web.Client
RUN dotnet publish "AlphaParking.Web.Client.csproj" -c Release -o /app

FROM base AS final
WORKDIR /app
COPY --from=publish /app .
ENTRYPOINT ["dotnet", "AlphaParking.Web.Client.dll"]