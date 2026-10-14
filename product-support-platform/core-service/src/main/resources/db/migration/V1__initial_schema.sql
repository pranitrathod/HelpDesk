CREATE TABLE app_users (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  full_name VARCHAR(120) NOT NULL,
  email VARCHAR(254) NOT NULL,
  password_hash VARCHAR(100) NOT NULL,
  role VARCHAR(30) NOT NULL,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  CONSTRAINT uk_app_users_email UNIQUE (email)
) ENGINE=InnoDB;

CREATE TABLE products (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  sku VARCHAR(80) NOT NULL,
  name VARCHAR(160) NOT NULL,
  description VARCHAR(2000),
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  CONSTRAINT uk_products_sku UNIQUE (sku)
) ENGINE=InnoDB;

CREATE TABLE customer_orders (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  customer_id BIGINT NOT NULL,
  status VARCHAR(30) NOT NULL,
  created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  CONSTRAINT fk_orders_customer FOREIGN KEY (customer_id) REFERENCES app_users(id),
  INDEX idx_orders_customer_created (customer_id, created_at)
) ENGINE=InnoDB;

CREATE TABLE order_items (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  order_id BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  quantity INT NOT NULL,
  CONSTRAINT ck_order_item_quantity CHECK (quantity > 0),
  CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES customer_orders(id),
  CONSTRAINT fk_order_items_product FOREIGN KEY (product_id) REFERENCES products(id),
  INDEX idx_order_items_product (product_id)
) ENGINE=InnoDB;

CREATE TABLE tickets (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  customer_id BIGINT NOT NULL,
  order_item_id BIGINT NOT NULL,
  issue_description VARCHAR(4000) NOT NULL,
  status VARCHAR(30) NOT NULL,
  priority VARCHAR(20) NOT NULL,
  assigned_to BIGINT NULL,
  created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  version BIGINT NOT NULL DEFAULT 0,
  CONSTRAINT fk_tickets_customer FOREIGN KEY (customer_id) REFERENCES app_users(id),
  CONSTRAINT fk_tickets_order_item FOREIGN KEY (order_item_id) REFERENCES order_items(id),
  CONSTRAINT fk_tickets_assignee FOREIGN KEY (assigned_to) REFERENCES app_users(id),
  INDEX idx_tickets_customer_created (customer_id, created_at),
  INDEX idx_tickets_status_created (status, created_at),
  INDEX idx_tickets_order_item (order_item_id)
) ENGINE=InnoDB;

CREATE TABLE ticket_comments (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  ticket_id BIGINT NOT NULL,
  author_id BIGINT NOT NULL,
  author_role VARCHAR(30) NOT NULL,
  content VARCHAR(5000) NOT NULL,
  created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  CONSTRAINT fk_comments_ticket FOREIGN KEY (ticket_id) REFERENCES tickets(id),
  CONSTRAINT fk_comments_author FOREIGN KEY (author_id) REFERENCES app_users(id),
  INDEX idx_comments_ticket_created (ticket_id, created_at)
) ENGINE=InnoDB;

CREATE TABLE ticket_status_history (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  ticket_id BIGINT NOT NULL,
  old_status VARCHAR(30),
  new_status VARCHAR(30) NOT NULL,
  actor_id BIGINT NOT NULL,
  note VARCHAR(1000),
  changed_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  CONSTRAINT fk_history_ticket FOREIGN KEY (ticket_id) REFERENCES tickets(id),
  CONSTRAINT fk_history_actor FOREIGN KEY (actor_id) REFERENCES app_users(id),
  INDEX idx_history_ticket_time (ticket_id, changed_at)
) ENGINE=InnoDB;

CREATE TABLE idempotency_keys (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  idempotency_key VARCHAR(100) NOT NULL,
  request_hash VARCHAR(64) NOT NULL,
  ticket_id BIGINT NULL,
  created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  CONSTRAINT fk_idempotency_user FOREIGN KEY (user_id) REFERENCES app_users(id),
  CONSTRAINT fk_idempotency_ticket FOREIGN KEY (ticket_id) REFERENCES tickets(id),
  CONSTRAINT uk_idempotency_user_key UNIQUE (user_id, idempotency_key),
  INDEX idx_idempotency_created (created_at)
) ENGINE=InnoDB;

CREATE TABLE outbox_events (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  topic VARCHAR(200) NOT NULL,
  event_type VARCHAR(100) NOT NULL,
  aggregate_key VARCHAR(120) NOT NULL,
  payload LONGTEXT NOT NULL,
  status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
  attempts INT NOT NULL DEFAULT 0,
  created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  published_at TIMESTAMP(6) NULL,
  last_error VARCHAR(1000) NULL,
  INDEX idx_outbox_status_created (status, created_at)
) ENGINE=InnoDB;

CREATE TABLE service_quotes (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  ticket_id BIGINT NOT NULL,
  created_by BIGINT NOT NULL,
  amount_paise BIGINT NOT NULL,
  currency VARCHAR(3) NOT NULL DEFAULT 'INR',
  description VARCHAR(500) NOT NULL,
  status VARCHAR(20) NOT NULL DEFAULT 'OPEN',
  expires_at TIMESTAMP(6) NOT NULL,
  created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  CONSTRAINT ck_quote_amount CHECK (amount_paise >= 100 AND amount_paise <= 100000000),
  CONSTRAINT fk_quotes_ticket FOREIGN KEY (ticket_id) REFERENCES tickets(id),
  CONSTRAINT fk_quotes_creator FOREIGN KEY (created_by) REFERENCES app_users(id),
  INDEX idx_quotes_ticket_status (ticket_id, status)
) ENGINE=InnoDB;

CREATE TABLE payments (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  quote_id BIGINT NOT NULL,
  provider VARCHAR(30) NOT NULL,
  provider_order_id VARCHAR(100) NULL,
  provider_payment_id VARCHAR(100) NULL,
  amount_paise BIGINT NOT NULL,
  currency VARCHAR(3) NOT NULL,
  status VARCHAR(20) NOT NULL,
  created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  CONSTRAINT ck_payment_amount CHECK (amount_paise >= 100),
  CONSTRAINT fk_payments_quote FOREIGN KEY (quote_id) REFERENCES service_quotes(id),
  CONSTRAINT uk_payment_provider_order UNIQUE (provider, provider_order_id),
  INDEX idx_payments_quote_status (quote_id, status)
) ENGINE=InnoDB;

CREATE TABLE processed_events (
  event_id VARCHAR(36) NOT NULL PRIMARY KEY,
  processed_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
) ENGINE=InnoDB;
