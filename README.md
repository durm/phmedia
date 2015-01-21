# Ph-media
## Static content storage
### Usage

- Create ```POST / (with ':file' param)
curl -XPOST -F file=@<path_to_file> <ph_media_host>```

- Read ```GET /':id'
curl -XGET <ph_media_host>/:id```

- Update ```PUT /':id' (with ':file' param)
curl -XPUT -F file=@<path_to_file> <ph_media_host>/:id```

- Delete ```DELETE /':id'
curl -XDELETE <ph_media_host>/:id```

### To clone and start it do:

```
git clone https://github.com/durm/phmedia.git
cd phmedia
lein ring server-headless
```
