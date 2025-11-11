# BaseResponseModel API Response Format

## Overview
All API endpoints now return a standardized response format with `status`, `message`, and `data` fields. This allows the frontend to easily catch messages, errors, and data.

## Response Structure
```json
{
  "status": 200,
  "message": "Success message",
  "data": {}
}
```

### Fields
- **status** (int): HTTP status code
  - 200: Success
  - 201: Created
  - 400: Bad Request
  - 401: Unauthorized
  - 403: Forbidden
  - 404: Not Found
  - 500: Internal Server Error

- **message** (string): Human-readable message describing the operation
- **data** (T): Generic type that holds the actual response data (can be null for errors)

## Usage Examples

### Success Response - Login
**Request:**
```bash
POST /api/auth/login
Content-Type: application/json

{
  "username": "user@example.com",
  "password": "password123"
}
```

**Response (200 OK):**
```json
{
  "status": 200,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
}
```

### Success Response - Get All Hotels
**Request:**
```bash
GET /api/hotels
Authorization: Bearer {token}
```

**Response (200 OK):**
```json
{
  "status": 200,
  "message": "Success",
  "data": [
    {
      "id": 1,
      "name": "Hotel Paradise",
      "location": "Hanoi"
    }
  ]
}
```

### Created Response
**Request:**
```bash
POST /api/hotels
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "New Hotel",
  "location": "Ho Chi Minh"
}
```

**Response (201 Created):**
```json
{
  "status": 201,
  "message": "Hotel created successfully",
  "data": {
    "id": 2,
    "name": "New Hotel",
    "location": "Ho Chi Minh"
  }
}
```

### Error Response - Not Found
**Request:**
```bash
GET /api/hotels/999
Authorization: Bearer {token}
```

**Response (404 Not Found):**
```json
{
  "status": 404,
  "message": "Hotel not found",
  "data": null
}
```

### Error Response - Bad Request
**Request:**
```bash
POST /api/auth/register
Content-Type: application/json

{
  "username": "existinguser",
  "password": "123456",
  "roles": "ROLE_USER"
}
```

**Response (400 Bad Request):**
```json
{
  "status": 400,
  "message": "Username already exists",
  "data": null
}
```

### Error Response - Unauthorized
**Response (401 Unauthorized):**
```json
{
  "status": 401,
  "message": "Incorrect username or password",
  "data": null
}
```

## Frontend Usage - JavaScript/TypeScript Example

```javascript
// Fetch API example
async function fetchHotels() {
  const response = await fetch('/api/hotels', {
    headers: {
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    }
  });
  
  const result = await response.json();
  
  if (result.status === 200) {
    console.log('Hotels:', result.data);
  } else {
    console.error('Error:', result.message);
  }
}

// Axios example
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8081'
});

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Login
async function login(username, password) {
  try {
    const { data } = await api.post('/api/auth/login', { username, password });
    if (data.status === 200) {
      localStorage.setItem('token', data.data.token);
      console.log(data.message); // "Login successful"
      return data.data;
    } else {
      console.error(data.message);
    }
  } catch (error) {
    console.error('Login failed:', error);
  }
}

// Get all hotels
async function getHotels() {
  try {
    const { data } = await api.get('/api/hotels');
    if (data.status === 200) {
      console.log(data.message); // "Success"
      return data.data; // List of hotels
    } else {
      console.error(data.message);
    }
  } catch (error) {
    console.error('Failed to fetch hotels:', error);
  }
}

// Create hotel
async function createHotel(hotelData) {
  try {
    const { data } = await api.post('/api/hotels', hotelData);
    if (data.status === 201) {
      console.log(data.message); // "Hotel created successfully"
      return data.data; // Created hotel
    } else {
      console.error(data.message);
    }
  } catch (error) {
    console.error('Failed to create hotel:', error);
  }
}
```

## Helper Methods in BaseResponseModel

The `BaseResponseModel` class provides static helper methods for creating standardized responses:

```java
// Success responses
BaseResponseModel.success(data) // Status 200
BaseResponseModel.success(data, "Custom message")

BaseResponseModel.created(data) // Status 201
BaseResponseModel.created(data, "Custom message")

// Error responses
BaseResponseModel.badRequest("message") // Status 400
BaseResponseModel.unauthorized("message") // Status 401
BaseResponseModel.forbidden("message") // Status 403
BaseResponseModel.notFound("message") // Status 404
BaseResponseModel.error("message") // Status 500
BaseResponseModel.error(status, "message") // Custom status
```

## All Updated Controllers
- ✅ AuthController - Login & Register
- ✅ HotelController - CRUD operations
- ✅ RoomController - CRUD operations
- ✅ AssetController - CRUD operations
- ✅ UserController - User management
