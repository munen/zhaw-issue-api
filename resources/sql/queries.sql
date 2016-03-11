-- :name create-issue! :<! :n
-- :doc creates a new issue
INSERT INTO issues
(cid, done, title, due_date)
VALUES (:cid, :done, :title, :due_date)
returning id

-- :name update-issue! :! :n
-- :doc update an existing issue
UPDATE issues
SET title = :title, done = :done, due_date = :due_date
WHERE id = :id

-- :name get-issue :? :1
-- :doc retrieve a issue given the id.
SELECT * FROM issues
WHERE id = :id

-- :name get-issues :? :*
-- :doc retrieves all issues
SELECT * FROM issues
order by id

-- :name delete-issue! :! :n
-- :doc delete a issue given the id
DELETE FROM issues
WHERE id = :id
