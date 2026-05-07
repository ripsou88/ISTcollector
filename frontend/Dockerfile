# syntax=docker/dockerfile:1

# Comments are provided throughout this file to help you get started.
# If you need more help, visit the Dockerfile reference guide at
# https://docs.docker.com/go/dockerfile-reference/

# Want to help us make this template better? Share your feedback here: https://forms.gle/ybq9Krt8jtBL3iCk7

ARG NODE_VERSION=24.15.0
ARG NGINX_VERSION=alpine3.22

################################################################################
# Use node image for base image for all stages.
FROM node:${NODE_VERSION}-alpine AS builder

# Set working directory for all build stages.
WORKDIR /usr/src/app


# Copy package-related files first to leverage Docker's caching mechanism
COPY frontend/package.json *frontend/package-lock.json* ./

# Install project dependencies using npm ci (ensures a clean, reproducible install)
RUN --mount=type=cache,target=/root/.npm npm ci

# Copy the rest of the source files into the image.
COPY frontend/. .
# Run the build script.
RUN npm run build

################################################################################
FROM nginxinc/nginx-unprivileged:${NGINX_VERSION} AS runner

# Copy custom Nginx config
COPY frontend/nginx.conf /etc/nginx/nginx.conf

# Copy the static build output from the build stage to Nginx's default HTML serving directory
COPY --chown=nginx:nginx --from=builder /usr/src/app/dist/frontend/browser /usr/share/nginx/html

# Use a built-in non-root user for security best practices
USER nginx


# Expose the port that the application listens on.
EXPOSE 8083

# Start Nginx directly with custom config
ENTRYPOINT ["nginx", "-c", "/etc/nginx/nginx.conf"]
CMD ["-g", "daemon off;"]

# Run the application.
#CMD npm start
