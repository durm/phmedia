# Ph-media
## Static content storage
### Usage

- Create
    POST / (with `:file` param)
- Read
    GET /`:id`
- Update
    PUT /`:id` (with `:file` param)
- Delete
    DELETE /`:id`

### To clone and start it do:

 git clone https://github.com/durm/phmedia.git
 cd phmedia
 lein ring server-headless
