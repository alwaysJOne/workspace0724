# API Documentation for 07_RestServer/jpa

This document outlines the entity structures and API endpoints provided by the `07_RestServer/jpa` project.

## Entity Information

This section describes the database entities (tables) used in the project. Most entities inherit `createDate` and `modifyDate` from `BaseTimeEntity`.

### BaseTimeEntity (Abstract)
Provides common auditing fields for other entities.
*   `createDate`: `LocalDateTime` - Timestamp of creation.
*   `modifyDate`: `LocalDateTime` - Timestamp of last modification.

### Board
Represents a board post.
*   `boardId`: `Long` - Primary Key, Auto Generated.
*   `boardTitle`: `String` (length 100) - Title of the board post. (Not Null)
*   `boardContent`: `String` (CLOB) - Content of the board post. (Not Null)
*   `originName`: `String` (length 100) - Original filename if a file is attached.
*   `changeName`: `String` (length 100) - Changed/stored filename.
*   `count`: `Integer` - View count of the post. (Default: 0)
*   `status`: `String` (`CommonEnums.Status` enum: 'Y', 'N') - Status of the board post. (Default: 'Y', Not Null)
*   `member`: `Member` - Many-to-One relationship with the Member who wrote the post. (Not Null)
*   `boardTags`: `List<BoardTag>` - One-to-Many relationship with associated tags.
*   Inherits `createDate`, `modifyDate`.

### BoardTag
Represents the many-to-many relationship between `Board` and `Tag`.
*   `boardTagId`: `Long` - Primary Key, Auto Generated.
*   `board`: `Board` - Many-to-One relationship with the associated board. (Not Null)
*   `tag`: `Tag` - Many-to-One relationship with the associated tag. (Not Null)

### Member
Represents a user/member of the system.
*   `userId`: `String` (length 30) - Primary Key, Unique identifier for the member.
*   `userPwd`: `String` (length 100) - Password of the member. (Not Null)
*   `userName`: `String` (length 15) - Name of the member. (Not Null)
*   `email`: `String` (length 255) - Email address of the member.
*   `gender`: `String` (`Member.Gender` enum: 'M', 'F') - Gender of the member. (length 1)
*   `age`: `Integer` - Age of the member.
*   `phone`: `String` (length 13) - Phone number of the member.
*   `address`: `String` (length 100) - Address of the member.
*   `status`: `String` (`CommonEnums.Status` enum: 'Y', 'N') - Status of the member account. (Default: 'Y', Not Null)
*   `profile`: `Profile` - One-to-One relationship with the member's profile.
*   `boards`: `List<Board>` - One-to-Many relationship with boards written by the member.
*   Inherits `createDate`, `modifyDate`.

### Notice
Represents a notice post.
*   `noticeId`: `Long` - Primary Key, Auto Generated.
*   `noticeTitle`: `String` (length 30) - Title of the notice. (Not Null)
*   `noticeContent`: `String` (length 200) - Content of the notice. (Not Null)
*   `member`: `Member` - Many-to-One relationship with the Member who wrote the notice. (Not Null)
*   Inherits `createDate`, `modifyDate`.

### Profile
Represents a member's profile information.
*   `profileId`: `Long` - Primary Key, Auto Generated.
*   `profileImage`: `String` (length 100) - Path or URL to the profile image.
*   `intro`: `String` (length 300) - Member's introduction/bio.
*   `member`: `Member` - One-to-One relationship with the associated member. (Not Null, Unique)

### Reply
Represents a reply/comment to a board post.
*   `replyNo`: `Long` - Primary Key, Auto Generated.
*   `replyContent`: `String` (length 400) - Content of the reply. (Not Null)
*   `status`: `String` (`CommonEnums.Status` enum: 'Y', 'N') - Status of the reply. (Default: 'Y', Not Null)
*   `board`: `Board` - Many-to-One relationship with the board post it replies to. (Not Null)
*   `member`: `Member` - Many-to-One relationship with the Member who wrote the reply. (Not Null)
*   Inherits `createDate`, `modifyDate`.

### Tag
Represents a tag that can be associated with board posts.
*   `tagId`: `Long` - Primary Key, Auto Generated.
*   `tagName`: `String` (length 30) - Name of the tag. (Not Null, Unique)


## API Endpoints

This section outlines the API endpoints provided by the `07_RestServer/jpa` project, derived from the `BoardController` and `MemberController` classes.

## Board API (`/api/board`)

### 1. Create a New Board

*   **Endpoint:** `POST /api/board`
*   **Description:** Creates a new board entry.
*   **Request Type:** `POST`
*   **Request Body (Form Data):**
    *   `board_title`: `String` - The title of the board.
    *   `board_content`: `String` - The content of the board.
    *   `user_id`: `String` - The ID of the user creating the board.
    *   `file`: `MultipartFile` - Optional file to upload with the board.
    *   `tags`: `List<String>` - Optional list of tags associated with the board.
*   **Response:** `ResponseEntity<Long>` - Returns the ID of the newly created board.
*   **Example Body (Conceptual, form data):**
    ```
    board_title=New Board Title
    board_content=Content of the new board
    user_id=member123
    tags=tag1,tag2
    ```

### 2. Get All Boards (Paginated)

*   **Endpoint:** `GET /api/board`
*   **Description:** Retrieves a paginated list of all boards.
*   **Request Type:** `GET`
*   **Query Parameters:**
    *   `page`: `Integer` - The page number to retrieve (0-indexed). Default: 0
    *   `size`: `Integer` - The number of items per page. Default: 5
    *   `sort`: `String` - Sorting criteria, e.g., `createDate,desc` for descending order by creation date. Default: `createDate,desc`
*   **Response:** `ResponseEntity<PageResponse<BoardDto.Response>>` - A paginated response containing board details.
    *   **`PageResponse` Object Fields:**
        *   `content`: `List<BoardDto.Response>` - A list of board response objects for the current page.
        *   `total_count`: `Long` - Total number of board entries across all pages.
        *   `current_page`: `Integer` - The current page number (0-indexed).
        *   `page_size`: `Integer` - The total number of pages available.
        *   `has_next`: `Boolean` - Indicates if there is a next page.
        *   `has_prev`: `Boolean` - Indicates if there is a previous page.
    *   **`BoardDto.Response` Object Fields (within `content`):**
        *   `board_id`: `Long` - Unique identifier of the board.
        *   `board_title`: `String` - The title of the board.
        *   `board_content`: `String` - The content of the board.
        *   `origin_name`: `String` - Original file name (if a file was uploaded).
        *   `change_name`: `String` - Stored file name (if a file was uploaded).
        *   `count`: `Integer` - View count of the board.
        *   `user_id`: `String` - The ID of the user who created the board.
        *   `user_name`: `String` - The name of the user who created the board.
        *   `create_date`: `LocalDateTime` - Timestamp of when the board was created.
        *   `tags`: `List<String>` - List of tags associated with the board.

### 3. Get Board Details by ID

*   **Endpoint:** `GET /api/board/{boardId}`
*   **Description:** Retrieves the detailed information for a specific board.
*   **Request Type:** `GET`
*   **Path Variable:** `boardId` (`Long`) - The unique identifier of the board.
*   **Response:** `ResponseEntity<BoardDto.Response>` - Details of the requested board.
    *   **`BoardDto.Response` Object Fields:** (Same as listed for `Get All Boards`)

### 4. Update an Existing Board

*   **Endpoint:** `PATCH /api/board/{boardId}`
*   **Description:** Updates the information of an existing board.
*   **Request Type:** `PATCH`
*   **Path Variable:** `boardId` (`Long`) - The unique identifier of the board to update.
*   **Request Body (Form Data):**
    *   `board_title`: `String` - New title for the board.
    *   `board_content`: `String` - New content for the board.
    *   `file`: `MultipartFile` - New file to upload (replaces existing file).
    *   `tags`: `List<String>` - New list of tags for the board.
*   **Response:** `ResponseEntity<BoardDto.Response>` - The updated board details.
    *   **`BoardDto.Response` Object Fields:** (Same as listed for `Get All Boards`)
*   **Example Body (Conceptual, form data):**
    ```
    board_title=Updated Board Title
    board_content=Updated content
    tags=newtag1,newtag2
    ```

### 5. Delete a Board

*   **Endpoint:** `DELETE /api/board/{boardId}`
*   **Description:** Deletes a specific board entry.
*   **Request Type:** `DELETE`
*   **Path Variable:** `boardId` (`Long`) - The unique identifier of the board to delete.
*   **Response:** `ResponseEntity<Void>` - No content on successful deletion.

## Member API (`/api/member`)

### 1. Register a New Member

*   **Endpoint:** `POST /api/member`
*   **Description:** Registers a new member in the system.
*   **Request Type:** `POST`
*   **Request Body (JSON):**
    *   `user_id`: `String` - Unique identifier for the member.
    *   `user_pwd`: `String` - Password for the member.
    *   `user_name`: `String` - Name of the member.
    *   `email`: `String` - Email address of the member.
    *   `gender`: `String` (`Member.Gender` enum: e.g., "MALE", "FEMALE", "OTHER") - Gender of the member.
    *   `age`: `Integer` - Age of the member.
    *   `phone`: `String` - Phone number of the member.
    *   `address`: `String` - Address of the member.
*   **Response:** `ResponseEntity<String>` - Returns the `user_id` of the newly created member.
*   **Example Body (JSON):**
    ```json
    {
        "user_id": "newuser123",
        "user_pwd": "password123",
        "user_name": "New User",
        "email": "newuser@example.com",
        "gender": "MALE",
        "age": 30,
        "phone": "010-1234-5678",
        "address": "Seoul"
    }
    ```

### 2. Get All Members

*   **Endpoint:** `GET /api/member`
*   **Description:** Retrieves a list of all registered members.
*   **Request Type:** `GET`
*   **Response:** `ResponseEntity<List<MemberDto.Response>>` - A list of member details.
    *   **`MemberDto.Response` Object Fields (within the list):**
        *   `user_id`: `String` - Unique identifier of the member.
        *   `user_name`: `String` - Name of the member.
        *   `email`: `String` - Email address of the member.
        *   `gender`: `String` (`Member.Gender` enum: e.g., "MALE", "FEMALE", "OTHER") - Gender of the member.
        *   `age`: `Integer` - Age of the member.
        *   `phone`: `String` - Phone number of the member.
        *   `address`: `String` - Address of the member.
        *   `create_date`: `LocalDateTime` - Timestamp of when the member was created.
        *   `modify_date`: `LocalDateTime` - Timestamp of when the member was last modified.

### 3. Get Member Details by User ID

*   **Endpoint:** `GET /api/member/{userId}`
*   **Description:** Retrieves the detailed information for a specific member using their user ID.
*   **Request Type:** `GET`
*   **Path Variable:** `userId` (`String`) - The unique identifier of the member.
*   **Response:** `ResponseEntity<MemberDto.Response>` - Details of the requested member.
    *   **`MemberDto.Response` Object Fields:** (Same as listed for `Get All Members`)

### 4. Update an Existing Member

*   **Endpoint:** `PUT /api/member/{userId}`
*   **Description:** Updates the information of an existing member.
*   **Request Type:** `PUT`
*   **Path Variable:** `userId` (`String`) - The unique identifier of the member to update.
*   **Request Body (JSON):**
    *   `user_name`: `String` - New name for the member.
    *   `email`: `String` - New email address for the member.
    *   `gender`: `String` (`Member.Gender` enum: e.g., "MALE", "FEMALE", "OTHER") - New gender for the member.
    *   `age`: `Integer` - New age for the member.
    *   `phone`: `String` - New phone number for the member.
    *   `address`: `String` - New address for the member.
*   **Response:** `ResponseEntity<MemberDto.Response>` - The updated member details.
    *   **`MemberDto.Response` Object Fields:** (Same as listed for `Get All Members`)
*   **Example Body (JSON):**
    ```json
    {
        "user_name": "Updated User Name",
        "email": "updated@example.com",
        "age": 31
    }
    ```

### 5. Delete a Member

*   **Endpoint:** `DELETE /api/member/{userId}`
*   **Description:** Deletes a specific member entry.
*   **Request Type:** `DELETE`
*   **Path Variable:** `userId` (`String`) - The unique identifier of the member to delete.
*   **Response:** `ResponseEntity<String>` - Returns "ok" on successful deletion.

### 6. Search Members by Name

*   **Endpoint:** `GET /api/member/search`
*   **Description:** Searches for members whose name matches a given keyword.
*   **Request Type:** `GET`
*   **Query Parameter:** `keyword` (`String`) - The keyword to search for within member names.
*   **Response:** `ResponseEntity<List<MemberDto.Response>>` - A list of members matching the search criteria.
    *   **`MemberDto.Response` Object Fields:** (Same as listed for `Get All Members`)
