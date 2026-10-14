# API examples

Base URL: `http://localhost:8080` (BFF). Use demo credentials only.

## Register and log in

```bash
curl -s http://localhost:8080/api/v1/auth/register -H 'Content-Type: application/json' \
  -d '{"fullName":"Demo Customer","email":"customer@example.com","password":"ChangeThisPassword123!"}'

curl -s http://localhost:8080/api/v1/auth/login -H 'Content-Type: application/json' \
  -d '{"email":"customer@example.com","password":"ChangeThisPassword123!"}'
```

Use returned `accessToken` as `TOKEN`. Admin/support role bootstrap is database-only in local development (see README).

## Create a product and delivered order (admin token)

```bash
curl -s http://localhost:8080/api/v1/admin/products -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H 'Content-Type: application/json' -d '{"sku":"PHONE-001","name":"Demo Phone","description":"Demo product"}'

curl -s http://localhost:8080/api/v1/admin/orders -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H 'Content-Type: application/json' -d '{"customerEmail":"customer@example.com","items":[{"productId":1,"quantity":1}],"status":"DELIVERED"}'
```

## Create an idempotent defect ticket (customer token)

```bash
curl -s http://localhost:8080/api/v1/tickets -X POST -H "Authorization: Bearer $TOKEN" \
  -H 'Idempotency-Key: 0f3d9431-customer-submit-001' -H 'Content-Type: application/json' \
  -d '{"orderItemId":1,"issueDescription":"Phone camera does not focus","priority":"HIGH"}'
```

Repeat with exactly the same key and body. The same ticket is returned. Reuse the key with a different payload and the server rejects the request.

## Assign a ticket (support/admin token)

```bash
curl -s http://localhost:8080/api/v1/tickets/1/assignment -X PATCH \
  -H "Authorization: Bearer $SUPPORT_TOKEN" -H 'Content-Type: application/json' \
  -d '{"supportAgentId":3}'
```

## Create a repair/inspection quote (support token)

```bash
curl -s http://localhost:8080/api/v1/tickets/1/quotes -X POST \
  -H "Authorization: Bearer $SUPPORT_TOKEN" -H 'Content-Type: application/json' \
  -d '{"amountPaise":49900,"description":"Optional out-of-warranty inspection fee"}'
```

`amountPaise` is trusted only from the support role; customers cannot set or change it.

## Initiate payment (customer token)

```bash
curl -s http://localhost:8080/api/v1/quotes/1/payment -X POST \
  -H "Authorization: Bearer $TOKEN"
```

Local profile returns a mock payment order. Complete it via the local-only mock route shown in the API response/docs. In production, configure Razorpay Checkout and a verified `order.paid` webhook; never mark payment captured from a frontend callback alone.
